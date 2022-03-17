package com.otopark.business.context;

import com.otopark.business.model.*;
import com.otopark.business.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
//public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
public class SetupDataLoader implements ApplicationListener<ApplicationReadyEvent> {

    boolean alreadySetup = false;

    private final RoleService roleService;
    private final PrivilegeService privilegeService;
    private final UserService userService;
    private final ParkingLineService parkingLineService;
    private final ParkingLotService parkingLotService;
    private final VehicleCategoryService vehicleCategoryService;
    private final VehicleTypeService vehicleTypeService;
    private final EmployeeService employeeService;
    private final MemberService memberService;
    private final ParkingTransactionService parkingTransactionService;
    private final ParkedVehicleService parkedVehicleService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());

    public SetupDataLoader(RoleService roleService, PrivilegeService privilegeService, UserService userService, ParkingLineService parkingLineService, ParkingLotService parkingLotService, VehicleCategoryService vehicleCategoryService, VehicleTypeService vehicleTypeService, EmployeeService employeeService, MemberService memberService, ParkingTransactionService parkingTransactionService, ParkedVehicleService parkedVehicleService) {
        this.roleService = roleService;
        this.privilegeService = privilegeService;
        this.userService = userService;
        this.parkingLineService = parkingLineService;
        this.parkingLotService = parkingLotService;
        this.vehicleCategoryService = vehicleCategoryService;
        this.vehicleTypeService = vehicleTypeService;
        this.employeeService = employeeService;
        this.memberService = memberService;
        this.parkingTransactionService = parkingTransactionService;
        this.parkedVehicleService = parkedVehicleService;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        initializeData();
    }

    public void initializeData() {
        try {
            Role adminRole = createRoleIfNotFound("ROLE_ADMIN",
                    Arrays.asList("PER_USERS",
                            "PER_TRANSACTIONS",
                            "PER_MEMBERS",
                            "PER_EMPLOYEES",
                            "PER_MODIFY_BASE_PRICE",
                            "PER_CHECK_IN",
                            "PER_CHECK_OUT"));
            Role oprtRole = createRoleIfNotFound("ROLE_OPRT",
                    Arrays.asList("PER_USERS",
                            "PER_TRANSACTIONS",
                            "PER_CHECK_IN",
                            "PER_CHECK_OUT"));
            Role accountantRole = createRoleIfNotFound("ROLE_ACCOUNTANT",
                    Arrays.asList("PER_TRANSACTIONS","PER_EMPLOYEES","PER_MODIFY_BASE_PRICE"));

            List<Role> admin = Arrays.asList(adminRole);
            List<Role> opr = Arrays.asList(oprtRole);
            List<Role> oprPlus = Arrays.asList(oprtRole,accountantRole);
            List<Role> acc = Arrays.asList(accountantRole);

            createUserIfNotFound("admin", "admin","admin@parkinglot.com","System","Admin",admin);
            createUserIfNotFound("opr1", "opr1","opr1@parkinglot.com","Ahmet","Demir",opr);
            createUserIfNotFound("opr2", "opr2","opr2@parkinglot.com","Ali","Çelik",oprPlus);
            createUserIfNotFound("opr3", "opr3","opr3@parkinglot.com","Merve","Akça",acc);

            createVehiceCategoriesAndTypes();

            createMembers();

            Employee e1 = createEmployeeIfNotFound("E001","Ahmet","Demir",new Date());
            Employee e2 = createEmployeeIfNotFound("E002","Ali","Çelik",new Date());
            Employee e3 = createEmployeeIfNotFound("E003","Merve","Akça",new Date());
            Employee e4 = createEmployeeIfNotFound("E004","Yusuf","Kaya",new Date());

            createParkingLot();


            //Add vehicles...
            ParkingLine pl1 = parkingLineService.findByLineOrder(1).get();
            ParkingLine pl2 = parkingLineService.findByLineOrder(2).get();
            ParkingLine pl3 = parkingLineService.findByLineOrder(3).get();

            ParkingTransaction pt1 = creteTransaction("SEDAN","06AAA06",pl1,0.0,1.0,"12-03-2022","", null,"E001");
            ParkingTransaction pt2 = creteTransaction("VAN","06BBB06",pl1,2.0,4.0,"12-03-2022","", null,"E001");

            ParkingTransaction pt3 = creteTransaction("SEDAN","06CCC15",pl2,4.0,5.0,"12-03-2022","", null,"E001");

            ParkedVehicle pv = new ParkedVehicle();
            pv.setParkingLine(pl1);
            pv.setParkingTransaction(pt1);
            parkedVehicleService.save(pv);

            pv = new ParkedVehicle();
            pv.setParkingLine(pl1);
            pv.setParkingTransaction(pt2);
            parkedVehicleService.save(pv);

            pv = new ParkedVehicle();
            pv.setParkingLine(pl2);
            pv.setParkingTransaction(pt3);
            parkedVehicleService.save(pv);

          } catch (Exception e) {
           logger.info("An error in onApplicationEvent");
        }
    }

private ParkingTransaction creteTransaction(String vehType, String lPlate, ParkingLine pl, Double start, Double end, String enter, String exit, Double price, String empId) throws ParseException {
        VehicleType vehicleType = vehicleTypeService.findByName(vehType).get();
        Employee employee = (empId!= null ? employeeService.findByIdentityNumber(empId).get() : null);
        Optional<Member> optMember =   memberService.findByLicensePlate(lPlate);
        Member member = optMember.isPresent() ? optMember.get() : null;

    long oneDay = 24*60*60*1000;
    Date yesterday = new Date(System.currentTimeMillis()-oneDay);
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        ParkingTransaction pt = new ParkingTransaction();
        pt.setVehicleType(vehicleType);
        pt.setLicensePlate(lPlate);
        pt.setParkingLine(pl);
        pt.setPositionStart(start);
        pt.setPositionEnd(end);
        pt.setEnterTime(enter!="" ? sdf.parse(enter) : null);
        pt.setExitTime(exit!="" ? sdf.parse(exit) : null);;
        pt.setPrice(price);
        pt.setResponsibleEmployee(employee);
        pt.setMember(member);
        return parkingTransactionService.save(pt);
}

    @Transactional
    private void createVehiceCategoriesAndTypes(){
        List<VehicleCategory> vehicleCategorys = vehicleCategoryService.findAll();

        Optional<VehicleCategory> optBase = vehicleCategoryService.findByName("BASE");
        Optional<VehicleCategory> optA = vehicleCategoryService.findByName("A");
        Optional<VehicleCategory> optB = vehicleCategoryService.findByName("B");
        Optional<VehicleCategory> optC = vehicleCategoryService.findByName("C");

        VehicleCategory catBase =  optBase.isPresent() ? optBase.get() : new VehicleCategory("BASE",0,1,40.0);
        VehicleCategory catA =  optA.isPresent() ? optA.get() : new VehicleCategory("A",1,1,0.0);
        VehicleCategory catB =  optB.isPresent() ? optB.get() : new VehicleCategory("B",2,1.2,0.0);
        VehicleCategory catC =  optC.isPresent() ? optC.get() : new VehicleCategory("C",3,2,0.0);

        vehicleCategoryService.save(catBase);
        vehicleCategoryService.save(catA);
        vehicleCategoryService.save(catB);
        vehicleCategoryService.save(catC);

        List<VehicleType> vehicleTypes = vehicleTypeService.findAll();
        if(vehicleTypes.isEmpty()){
            VehicleType vehicleType = new VehicleType("MOTORCYCLE", catA);
            vehicleTypeService.save(vehicleType);

            vehicleType = new VehicleType("SEDAN", catA);
            vehicleTypeService.save(vehicleType);

            vehicleType = new VehicleType("HATCHBACK",catA);
            vehicleTypeService.save(vehicleType);

            vehicleType = new VehicleType("JEEP", catB);
            vehicleTypeService.save(vehicleType);

            vehicleType = new VehicleType("SUV", catB);
            vehicleTypeService.save(vehicleType);

            vehicleType = new VehicleType("MINIBUS", catC);
            vehicleTypeService.save(vehicleType);

            vehicleType = new VehicleType("VAN", catC);
            vehicleTypeService.save(vehicleType);
        }

    }

    @Transactional
    private void createMembers(){
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            Member member1 = new Member("Can", "Kaya", "1111", "06CCC15",sdf.parse("01-01-2021"),sdf.parse("01-01-2022") );
            Member member2 = new Member("Ebru", "Özkan", "2222", "06EEE15",sdf.parse("01-01-2022"),sdf.parse("01-01-2023") );
            Member member3 = new Member("Bilge", "Özdemir", "333", "06BBB15",sdf.parse("01-01-2022"),sdf.parse("01-01-2023") );

            memberService.save(member1);
            memberService.save(member2);
            memberService.save(member3);
        }
        catch (Exception e){
           logger.info("Member initialize error");
        }
    }

    @Transactional
    private Employee createEmployeeIfNotFound(String identityNumber, String firstName, String lastName, Date startDate){
        Optional<Employee> optEmployee = employeeService.findByIdentityNumber(identityNumber);
        if(!optEmployee.isPresent()) {
            Employee employee = new Employee();
            employee.setIdentityNumber(identityNumber);
            employee.setFirstName(firstName);
            employee.setLastName(lastName);
            employee.setStartDate(startDate);
            employeeService.save(employee);
            return employee;
        }
        else return optEmployee.get();
    }

    @Transactional
    private void createParkingLot() throws ParseException {
        ParkingLot parkingLot = parkingLotService.getInstance();
        parkingLot.setName("PARKING LOT");
        //parkingLotService.save(parkingLot);

        parkingLot.addLine(new ParkingLine(1, 6.0));
        parkingLot.addLine(new ParkingLine(2, 6.0));
        parkingLot.addLine(new ParkingLine(3, 6.0));
        parkingLot.addLine(new ParkingLine(4, 6.0));
        parkingLot.addLine(new ParkingLine(5, 6.0));
        parkingLot.addLine(new ParkingLine(6, 6.0));
        parkingLot.addLine(new ParkingLine(7, 6.0));
        parkingLot.addLine(new ParkingLine(8, 6.0));
        parkingLot.addLine(new ParkingLine(9, 2.0));
        parkingLotService.save(parkingLot);

    }

    @Transactional
    private User createUserIfNotFound(String userName, String password, String email, String firstName, String lastName, List<Role> roles){
        Optional<User> optUser = userService.findByUsername(userName);
        if(!optUser.isPresent()){
            User user = new User();
            user.setUsername(userName);
            user.setPassword(password);
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setRoles(roles);
            user = userService.save(user);
            return user;
        }
        else return optUser.get();
    }

    @Transactional
    Role createRoleIfNotFound(String name, List<String> privilegeNames) {

        Optional<Role> optRole = roleService.findByName(name);
        Role role;
        if (!optRole.isPresent()) {
            List<Privilege> rolePriveleges = new ArrayList<>();
            for(String privelegeName : privilegeNames){
                rolePriveleges.add(createPrivilegeIfNotFound(privelegeName));
            }
            role = new Role(name);
            role.setPrivileges(rolePriveleges);
            roleService.save(role);
        }
        else role = optRole.get();
        return role;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Optional<Privilege> optPrivilege = privilegeService.findByName(name);
        Privilege privilege;
        if (!optPrivilege.isPresent()) {
            privilege = new Privilege(name);
            privilegeService.save(privilege);
        }
        else privilege = optPrivilege.get();
        return privilege;
    }

    /*
    private void createCards(){
        DecimalFormat numFormat = new DecimalFormat("000");
        //Card Definitions
        List<Card> cards = cardService.findAll();
        if (cards.isEmpty()) {
            Card card;
            for(int i=1; i<11; i++) {
                card = new Card("CIM-"+ numFormat.format(i), CardType.MEMBER);
                cardService.save(card);
            }
            for(int i=1; i<11; i++) {
                card = new Card("CIV-"+ numFormat.format(i), CardType.VISITOR);
                cardService.save(card);
            }
        }
    }
    */
}