package com.example.analysisreport;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.contact_person.entity.ClientContactPerson;
import com.example.analysisreport.contact_person.repository.ClientContactPersonRepository;
import com.example.analysisreport.client.repository.ClientRepository;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.contract.entity.ContractType;
import com.example.analysisreport.contract.repository.ContractRepository;

import com.example.analysisreport.quality_indicator.repository.QualityIndicatorRepository;
import com.example.analysisreport.report.repository.ReportRepository;
import com.example.analysisreport.results.repository.ResultsRepository;
import com.example.analysisreport.samples.dto.WaterSampleCreateDto;
import com.example.analysisreport.samples.repository.SampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
// Same as @SpringBootConfiguration @EnableAutoConfiguration @ComponentScanScan
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
// to use DTOs in paginated responses (enable stable page serialization)
public class AnalysisReportApplication implements CommandLineRunner {
    @Autowired
    ClientRepository clientRepo;
    @Autowired
    ClientContactPersonRepository contactPersRepo;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    SampleRepository sampleRepository;
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    QualityIndicatorRepository indicatorRepository;
    @Autowired
    ResultsRepository resultsRepository;

    public static void main(String[] args) {
        SpringApplication.run(AnalysisReportApplication.class, args);
    }

    public void run(String... args) throws Exception {
        // Create and save clients
        //        Client client1 = new Client("Panifcom", "Str. V. Loghin, nr. 35, Iasi");
        //        Client client2 = new Client("Borgwarner", "Str. A. Paulescu, nr. 2, com. Miroslava, Iasi");
        //        Client client3 = new Client("Primaria Falticeni", "Str. G. Protopopescu, nr. 1, com. Falticeni,
        //        Botosani");
        //        Client client4 = new Client("Borgw", "Str. E. Lupu nr. 106, Galati");
        //        client1 = clientRepo.save(client1);
        //        client2 = clientRepo.save(client2);
        //        client3 = clientRepo.save(client3);
        //        client4 = clientRepo.save(client4);

        // Create and save contact persons
        //        ClientContactPerson contactPerson1 = new ClientContactPerson(client1, "Paula", "Avadani", "inginer",
        //                "pava****@panifcom.ro", "0752*****");
        //        ClientContactPerson contactPerson2 = new ClientContactPerson(client2, "Corina", "Volf",
        //        "responsabil " +
        //                "mediu", "cvo***@borgwarner.ro", "0782*****");
        //        ClientContactPerson contactPerson3 = new ClientContactPerson(client1, "George", "Brancus", "inginer",
        //                "gbra***@panifcom.ro", "02365*****");
        //        ClientContactPerson contactPerson4 = new ClientContactPerson(client4, "Valeriu", "Negoi", "inginer",
        //                "vn***@borgw.ro", "0254*****");
        //        contactPersRepo.save(contactPerson1);
        //        contactPersRepo.save(contactPerson2);
        //        contactPersRepo.save(contactPerson3);
        //        contactPersRepo.save(contactPerson4);
        //
        //        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");


        // insert
        // Create and save contracts
        //        Contract contract1 = new Contract("1366P");
        //        contract1.setClient(client1);
        //        contract1.setContractDate(LocalDate.of(2024, 8, 25));
        //        contract1.setContractType(ContractType.CONTRACT);
        //        contract1 = contractRepository.save(contract1);
        //
        //        Contract contract2 = new Contract("6");
        //        contract2.setClient(client2);
        //        contract2 = contractRepository.save(contract2);

        //        Contract contract3 = new Contract("1245698-63", formatter.parse("28.02.2022"), ContractType.ORDER);
        //        contract3.setClient(client3);
        //        contract3 = contractRepository.save(contract3);
        //
        //        Contract contract4 = new Contract("1301GL", formatter.parse("17.01.2022"), ContractType.ORDER);
        //        contract4.setClient(client4);
        //        contract4 = contractRepository.save(contract4);

        // create water samples without setting IDs manually
        //        WaterSampleCreateDto dto1 = new WaterSampleCreateDto();
        //        dto1.setSampleCode("sc-2-13.02.22");
        //        dto1.setClientId(client1.getId());
        //        dto1.setContractId(contract1.getId());
        //        dto1.setSamplingDateTime(formatter.parse("13.02.2022").toInstant().atZone(java.time.ZoneId
        //        .systemDefault()).toLocalDateTime());
        //        dto1.setReceivingDateTime(formatter.parse("14.02.2022").toInstant().atZone(java.time.ZoneId
        //        .systemDefault()).toLocalDateTime());
        //        dto1.setSampleLocationDetails("Statie de epurare Panifcom");
        //        dto1.setWaterSampleType(com.example.analysisreport.samples.entity.WaterSampleType.WASTEWATER);
        //        WaterSample sampleEntity1 = new WaterSample();
        //        sampleEntity1.setClient(client1);
        //        sampleEntity1.setContract(contract1);
        //        sampleEntity1.setSampleLocationDetails(dto1.getSampleLocationDetails());
        //        sampleEntity1.setType(dto1.getWaterSampleType());
        //        sampleEntity1 = sampleRepository.save(sampleEntity1);

        // Create and save water samples using actual IDs
        //        WaterSample sample1 = new WaterSample();
        //        sample1.setSampleCode("sc-2-13.02.22");
        //        sample1.setClient(client1);
        //        sample1.setContract(contract1);
        //        sample1.setSamplingDateTime(formatter.parse("13.02.2022"));
        //        sample1 = sampleRepository.save(sample1);
        //
        //        WaterSample sample2 = new WaterSample();
        //        sample2.setSampleCode("sc-3-17.03.22");
        //        sample2.setClientId(client1.getId());
        //        sample2.setContractId(contract1.getId());
        //        sample2.setSamplingDate(formatter.parse("17.03.2022"));
        //        sample2 = sampleRepository.save(sample2);
        //
        //        WaterSample sample3 = new WaterSample();
        //        sample3.setSampleCode("sc-1-06.04.22");
        //        sample3.setClientId(client3.getId());
        //        sample3.setContractId(contract3.getId());
        //        sample3.setSamplingDate(formatter.parse("06.04.2022"));
        //        sample3 = sampleRepository.save(sample3);
        //
        //        WaterSample sample4 = new WaterSample();
        //        sample4.setSampleCode("sc-2-06.04.22");
        //        sample4.setClientId(client1.getId());
        //        sample4.setContractId(contract1.getId());
        //        sample4.setSamplingDate(formatter.parse("06.04.2022"));
        //        sample4 = sampleRepository.save(sample4);

        // Create and save reports (do not set IDs manually)
        //        Report report1 = new Report();
        //        report1.setReportNumber(23);
        //        report1.setIssueDate(formatter.parse("21.02.2022"));
        //        report1.setWaterSample(sampleEntity1);
        //        report1.setAnalystId(1L); // Consider using actual analyst entity if available
        //        report1 = reportRepository.save(report1);
        //
        //        Report report2 = new Report();
        //        report2.setReportNumber(40);
        //        report2.setIssueDate(formatter.parse("11.04.2022"));
        ////        report2.setWaterSample(sample2);
        //        report2.setAnalystId(2L);
        ////        report2 = reportRepository.save(report2);
        //
        //        Report report3 = new Report();
        //        report3.setReportNumber(45);
        //        report3.setIssueDate(formatter.parse("18.04.2022"));
        ////        report3.setWaterSample(sample3);
        //        report3.setAnalystId(2L);
        ////        report3 = reportRepository.save(report3);
        //
        //        Report report4 = new Report();
        //        report4.setReportNumber(46);
        //        report4.setIssueDate(formatter.parse("18.04.2022"));
        ////        report4.setWaterSample(sample4);
        //        report4.setAnalystId(1L);
        ////        report4 = reportRepository.save(report4);
        //
        //        // Create and save quality indicators
        //        QualityIndicator fe1 = new QualityIndicator();
        //        fe1.setName("Fe");
        //        fe1.setMeasurementUnit("mg/L");
        //        fe1.setAccredited(AccreditedStatus.YES);
        //        fe1 = indicatorRepository.save(fe1);
        //
        //        QualityIndicator fe2 = new QualityIndicator();
        //        fe2.setName("Fe");
        //        fe2.setMeasurementUnit("ug/L");
        //        fe2.setAccredited(AccreditedStatus.YES);
        //        fe2 = indicatorRepository.save(fe2);
        //
        //        QualityIndicator fe3 = new QualityIndicator();
        //        fe3.setName("Fe");
        //        fe3.setMeasurementUnit("mg/L");
        //        fe3.setAccredited(AccreditedStatus.NO);
        //        fe3 = indicatorRepository.save(fe3);
        //
        //        QualityIndicator fe4 = new QualityIndicator();
        //        fe4.setName("Fe");
        //        fe4.setMeasurementUnit("ug/L");
        //        fe4.setAccredited(AccreditedStatus.NO);
        //        fe4 = indicatorRepository.save(fe4);
        //
        //        QualityIndicator cco = new QualityIndicator();
        //        cco.setName("CCO-Cr");
        //        cco.setMeasurementUnit("mg/L");
        //        cco.setAccredited(AccreditedStatus.YES);
        //        cco = indicatorRepository.save(cco);
        //
        //        // Create and save results using actual IDs
        //        Results r1 = new Results();
        //        r1.setSampleId(sampleEntity1.getId());
        //        r1.setReportId(report1.getId());
        //        r1.setQualityIndicatorId(fe1.getId());
        //        r1.setResult("360");
        //        resultsRepository.save(r1);
        //
        //        Results r2 = new Results();
        //        r2.setSampleId(sampleEntity1.getId());
        //        r2.setReportId(report1.getId());
        //        r2.setQualityIndicatorId(cco.getId());
        //        r2.setResult("<30");
        //        resultsRepository.save(r2);
        //
        //        Results r3 = new Results();
        ////        r3.setSampleId(sample2.getId());
        //        r3.setReportId(report2.getId());
        //        r3.setQualityIndicatorId(fe2.getId());
        //        r3.setResult("<3");
        //        resultsRepository.save(r3);
        //
        //        Results r4 = new Results();
        ////        r4.setSampleId(sample3.getId());
        //        r4.setReportId(report3.getId());
        //        r4.setQualityIndicatorId(cco.getId());
        //        r4.setResult("766");
        //        resultsRepository.save(r4);
        //
        //        Results r5 = new Results();
        ////        r5.setSampleId(sample4.getId());
        //        r5.setReportId(report4.getId());
        //        r5.setQualityIndicatorId(fe3.getId());
        //        r5.setResult("0.68");
        //        resultsRepository.save(r5);
    }
}
