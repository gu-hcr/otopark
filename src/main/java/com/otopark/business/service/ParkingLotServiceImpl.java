package com.otopark.business.service;

import com.otopark.business.model.*;
import com.otopark.business.repository.ParkingLotJPARepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ParkingLotServiceImpl extends AbstractServiceImpl<ParkingLot> implements ParkingLotService{
    @Autowired
    private ParkingLotJPARepository parkingLotJPARepository;

    @Autowired
    private ParkingLineService parkingLineService;
    @Autowired
    private ParkingTransactionService parkingTransactionService;
    @Autowired
    private VehicleCategoryService vehicleCategoryService;
    @Autowired
    private VehicleTypeService vehicleTypeService;
    @Autowired
    private ParkedVehicleService parkedVehicleService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private MemberService memberService;

    public ParkingLotServiceImpl(ParkingLotJPARepository parkingLotJPARepository) {
        super(parkingLotJPARepository);
        this.parkingLotJPARepository = parkingLotJPARepository;
    }

    @Override
    public ParkingLot getInstance() {
        Optional<ParkingLot> parkingLot = parkingLotJPARepository.findAll().stream().findFirst();
        return parkingLot.isPresent() ? parkingLot.get() : ParkingLot.getInstance();
    }

    @Override
    public Double getVehiclePrice(Long vehicleId){
        ParkedVehicle v = parkedVehicleService.findById(vehicleId);

        VehicleCategory category = v.getParkingTransaction().getVehicleType().getVehicleCategory();
        Date now = new Date();
        Date start = v.getParkingTransaction().getEnterTime();
        long diffInMillies = Math.abs(now.getTime() - start.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        diff = diff==0 ? 1 : diff;

        //find coefficient
        Double basePrice = vehicleCategoryService.findByName("BASE").get().getPrice();
        int categoryOrder = (diff>5 ? category.getCategoryOrder()-1 : category.getCategoryOrder());
        Double coefficient = vehicleCategoryService.findByCategoryOrder(categoryOrder).get().getCoefficient();

        Double totalPrice = new BigDecimal(basePrice.toString()).multiply(new BigDecimal(coefficient.toString())).doubleValue()* diff;
        Member member = v.getParkingTransaction().getMember();

        //%20 discount
        //if( member!= null && member.isValid()){//TODO :rule for partial active ?
        if(member != null){
            totalPrice = new BigDecimal(totalPrice.toString()).multiply(new BigDecimal("0.8")).doubleValue();
        }

        return totalPrice;
    }

    @Override
    public ParkedVehicle vehicleCheckin(String vehicleTypeName, String licensePlate, Long employeeId) {
        VehicleType vehicleType = vehicleTypeService.findByName(vehicleTypeName).get();
        Double vehicleUnit = vehicleType.getVehicleCategory().getCoefficient();
        Employee employee = employeeService.findById(employeeId);

        boolean placeExist = false;
        ParkedVehicle parkedVehicle = null;

        ParkingLine targetLine = null;
        Double targetPosition=0.0;

        ParkingLot parkingLot = getInstance();

        for(ParkingLine l : parkingLot.getParkingLines()){

            Double lastPosition = 0.0 ;
            List<ParkedVehicle> sortedV = l.getVehicles().stream().sorted(Comparator.comparing(x -> x.getParkingTransaction().getPositionStart())).collect(Collectors.toList());
            //for(ParkedVehicle v : l.getVehicles()){
            for(ParkedVehicle v : sortedV){
                Double vStart = v.getParkingTransaction().getPositionStart();

                BigDecimal bdVehStart = new BigDecimal(vStart.toString());
                BigDecimal bdLastPosition = new BigDecimal(lastPosition.toString());
                if(bdVehStart.subtract(bdLastPosition).compareTo( new BigDecimal(vehicleUnit)) >= 0){
                    targetLine = l;
                    targetPosition = lastPosition;
                    placeExist=true;
                    break;
                }
                lastPosition= v.getParkingTransaction().getPositionEnd();
            }
            if(placeExist) break;
            else{
                BigDecimal bdLineLength = new BigDecimal(l.getLength().toString());
                BigDecimal bdLastPosition = new BigDecimal(lastPosition.toString());
                if(bdLineLength.subtract(bdLastPosition).compareTo( new BigDecimal(vehicleUnit.toString())) >= 0){
                    targetLine = l;
                    targetPosition = lastPosition;
                    placeExist=true;
                    break;
                }
            }
        }

        if(placeExist) {
            Optional<Member> member = memberService.findByLicensePlate(licensePlate);
            //new
            ParkingTransaction parkingTransaction = new ParkingTransaction();
            parkingTransaction.setParkingLine(targetLine);
            parkingTransaction.setVehicleType(vehicleType);
            parkingTransaction.setLicensePlate(licensePlate);
            parkingTransaction.setPositionStart(targetPosition);
            Double positionEnd = (new BigDecimal(targetPosition.toString()).add(new BigDecimal(vehicleUnit.toString()))).doubleValue();
            parkingTransaction.setPositionEnd(positionEnd);
            parkingTransaction.setEnterTime(new Date());
            parkingTransaction.setResponsibleEmployee(employee);
            parkingTransaction.setMember(member.isPresent() && member.get().isValid() ? member.get() : null);
            parkingTransactionService.save(parkingTransaction);
            parkedVehicle = new ParkedVehicle(parkingTransaction);
            targetLine.addVehicle(parkedVehicle);

            parkingLineService.save(targetLine);
            return parkedVehicle;
        }
        return null;//TODO : custom response mess
    }

    @Override
    public void vehicleCheckout( Long vehicleId, Double price) {
        ParkedVehicle parkedVehicle = parkedVehicleService.findById(vehicleId);
        Double priceControl = getVehiclePrice(vehicleId);

        if(priceControl.equals(price)) {
            ParkingTransaction transaction = parkedVehicle.getParkingTransaction();
            transaction.setPrice(price);
            transaction.setExitTime(new Date());
            parkingTransactionService.save(transaction);
            parkingLineService.removeVehicle(parkedVehicle);
        }
        else{
            throw new RuntimeException("Total cost is different. Please try again!");
        }

    }

}
