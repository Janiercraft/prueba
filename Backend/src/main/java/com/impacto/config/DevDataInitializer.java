package com.impacto.config;
import com.impacto.entity.*;
import com.impacto.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import java.time.*;
import java.math.*;
@Configuration @Profile("dev") public class DevDataInitializer {
    @Bean CommandLineRunner seed(UserRepository users,FamilyRepository families,ChildRepository children,NeedRepository needs,DonorRepository donors,
        CampaignRepository campaigns,DonationRepository donations,ResourceRepository resources) {
        return args-> {
            if(users.count()>0)return;
            User admin=u(users,"Admin Demo","admin@impacto.local","Admin123!",Role.ADMIN);
            User vol=u(users,"Voluntario Demo","volunteer@impacto.local","Volunteer123!",Role.VOLUNTEER);
            User donUser=u(users,"Donante Demo","donor@impacto.local","Donor123!",Role.DONOR);
            Donor donor=new Donor();
            donor.setUser(donUser);
            donor.setName("Donante Demo");
            donor.setType(DonorType.PERSON);
            donor.setEmail(donUser.getEmail());
            donors.save(donor);
            Family f=new Family();
            f.setContactName("Familia Pérez");
            f.setPhone("3000000000");
            f.setAddress("Barrio Central");
            f.setMunicipality("Turbo");
            f.setStatus(FamilyStatus.ACTIVE);
            families.save(f);
            Child c=new Child();
            c.setFamily(f);
            c.setName("Ana Pérez");
            c.setBirthDate(LocalDate.of(2017,5,10));
            c.setSchool("Institución Demo");
            c.setGrade("3");
            c.setStatus(ChildStatus.ACTIVE);
            children.save(c);
            Need n=new Need();
            n.setFamily(f);
            n.setChild(c);
            n.setType(NeedType.COMPUTER);
            n.setPriority(Priority.HIGH);
            n.setStatus(NeedStatus.PENDING);
            n.setDescription("Computador para actividades escolares");
            n.setRequestedQuantity(1);
            n.setFulfilledQuantity(0);
            needs.save(n);
            Campaign camp=new Campaign();
            camp.setName("Computadores para niños");
            camp.setDescription("Campaña de desarrollo digital");
            camp.setGoal(new BigDecimal("5000000"));
            camp.setStartDate(LocalDate.now());
            camp.setEndDate(LocalDate.now().plusDays(30));
            camp.setStatus(CampaignStatus.ACTIVE);
            campaigns.save(camp);
            Donation d=new Donation();
            d.setDonor(donor);
            d.setCampaign(camp);
            d.setType(DonationType.MATERIAL);
            d.setDescription("Computador portátil demo");
            d.setQuantity(1);
            d.setMaterialType(ResourceType.COMPUTER);
            d.setStatus(DonationStatus.RECEIVED);
            donations.save(d);
            Resource res=new Resource();
            res.setCode("PC-000001");
            res.setType(ResourceType.COMPUTER);
            res.setDescription(d.getDescription());
            res.setQuantity(1);
            res.setAvailableQuantity(1);
            res.setStatus(ResourceStatus.AVAILABLE);
            res.setDonation(d);
            resources.save(res);
        };
    }
    private User u(UserRepository r,String n,String email,String p,Role role) {
        User u=new User();
        u.setName(n);
        u.setEmail(email);
        u.setPassword(p);
        u.setRole(role);
        return r.save(u);
    }
}
