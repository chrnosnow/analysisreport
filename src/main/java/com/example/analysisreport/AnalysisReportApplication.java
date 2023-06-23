package com.example.analysisreport;

import com.example.analysisreport.client.entity.Client;
import com.example.analysisreport.contact_person.entity.ClientContactPerson;
import com.example.analysisreport.contact_person.repository.ClientContactPersonRepository;
import com.example.analysisreport.client.repository.ClientRepository;
import com.example.analysisreport.contract.entity.Contract;
import com.example.analysisreport.contract.entity.ContractType;
import com.example.analysisreport.contract.repository.ContractRepository;
import com.example.analysisreport.quality_indicator.entity.AccreditedStatus;
import com.example.analysisreport.quality_indicator.entity.QualityIndicator;
import com.example.analysisreport.quality_indicator.repository.QualityIndicatorRepository;
import com.example.analysisreport.report.entity.Report;
import com.example.analysisreport.report.repository.ReportRepository;
import com.example.analysisreport.results.entity.Results;
import com.example.analysisreport.results.repository.ResultsRepository;
import com.example.analysisreport.water_sample.entity.WaterSample;
import com.example.analysisreport.water_sample.repository.WaterSampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;

@SpringBootApplication
public class AnalysisReportApplication implements CommandLineRunner {
    @Autowired
    ClientRepository clientRepo;
    @Autowired
    ClientContactPersonRepository contactPersRepo;
    @Autowired
    ReportRepository reportRepository;
    @Autowired
    WaterSampleRepository sampleRepository;
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
        Client client1 = new Client(1L, "Panifcom", "Str. V. Loghin, nr. 35, Iasi");
        Client client2 = new Client(2L, "Borgwarner", "Str. A. Paulescu, nr. 2, com. Miroslava, Iasi");
        Client client3 = new Client(3L, "Primaria Falticeni", "Str. G. Protopopescu, nr. 1, com. Falticeni, Botosani");
        Client client4 = new Client(4L, "Borgw", "Str. E. Lupu nr. 106, Galati");
        clientRepo.save(client1);
        clientRepo.save(client2);
        clientRepo.save(client3);
        clientRepo.save(client4);

        ClientContactPerson contactPerson1 = new ClientContactPerson(1L, client1, "Paula", "Avadani", "inginer",
                "pava****@panifcom.ro", "0752*****");
        ClientContactPerson contactPerson2 = new ClientContactPerson(2L, client2, "Corina", "Volf", "responsabil " +
                "mediu", "cvo***@borgwarner.ro", "0782*****");
        ClientContactPerson contactPerson3 = new ClientContactPerson(3L, client1, "George", "Brancus", "inginer",
                "gbra***@panifcom.ro", "02365*****");
        ClientContactPerson contactPerson4 = new ClientContactPerson(4L, client4, "Valeriu", "Negoi", "inginer",
                "vn***@borgw.ro", "0254*****");

        contactPersRepo.save(contactPerson1);
        contactPersRepo.save(contactPerson2);
        contactPersRepo.save(contactPerson3);
        contactPersRepo.save(contactPerson4);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");

        Contract contract1 = new Contract("1366P", formatter.parse("06.01.2022"), ContractType.CONTRACT);
        //		contractRepository.save(contract1);
        contract1.setClient(client1);
        contractRepository.save(contract1);


        Contract contract2 = new Contract("6", formatter.parse("22.02.2022"), ContractType.ORDER);
        //		contractRepository.save(contract2);
        contract1.setClient(client2);
        contractRepository.save(contract2);

        Contract contract3 = new Contract("1245698-63", formatter.parse("28.02.2022"), ContractType.ORDER);
        //		contractRepository.save(contract3);
        contract1.setClient(client3);
        contractRepository.save(contract3);

        Contract contract4 = new Contract("1301GL", formatter.parse("17.01.2022"), ContractType.ORDER);
        //		contractRepository.save(contract4);
        contract1.setClient(client4);
        contractRepository.save(contract4);

        WaterSample sample1 = new WaterSample();
        sample1.setSampleCode("sc-2-13.02.22");
        sample1.setClientId(1L);
        sample1.setContractId(1L);
        sample1.setSamplingDate(formatter.parse("13.02.2022"));
        sampleRepository.save(sample1);

        WaterSample sample2 = new WaterSample();
        sample2.setSampleCode("sc-3-17.03.22");
        sample2.setClientId(1L);
        sample2.setContractId(1L);
        sample2.setSamplingDate(formatter.parse("17.03.2022"));
        sampleRepository.save(sample2);

        WaterSample sample3 = new WaterSample();
        sample3.setSampleCode("sc-1-06.04.22");
        sample3.setClientId(3L);
        sample3.setContractId(3L);
        sample3.setSamplingDate(formatter.parse("06.04.2022"));
        sampleRepository.save(sample3);

        WaterSample sample4 = new WaterSample();
        sample4.setSampleCode("sc-2-06.04.22");
        sample4.setClientId(1L);
        sample4.setContractId(1L);
        sample4.setSamplingDate(formatter.parse("06.04.2022"));
        sampleRepository.save(sample4);

        Report report1 = new Report();
        report1.setId(1L);
        report1.setReportNumber(23);
        report1.setIssueDate(formatter.parse("21.02.2022"));
        report1.setWaterSample(sample1);
        report1.setAnalystId(1L);
        reportRepository.save(report1);

        Report report2 = new Report();
        report2.setId(10L);
        report2.setReportNumber(40);
        report2.setIssueDate(formatter.parse("11.04.2022"));
        report2.setWaterSample(sample2);
        report2.setAnalystId(2L);
        reportRepository.save(report2);

        Report report3 = new Report();
        report3.setId(20L);
        report3.setReportNumber(45);
        report3.setIssueDate(formatter.parse("18.04.2022"));
        report3.setWaterSample(sample3);
        report3.setAnalystId(2L);
        reportRepository.save(report3);

        Report report4 = new Report();
        report4.setId(21L);
        report4.setReportNumber(46);
        report4.setIssueDate(formatter.parse("18.04.2022"));
        report4.setWaterSample(sample4);
        report4.setAnalystId(1L);
        reportRepository.save(report4);

        QualityIndicator fe1 = new QualityIndicator();
        fe1.setName("Fe");
        fe1.setMeasurementUnit("mg/L");
        fe1.setAccredited(AccreditedStatus.YES);
        indicatorRepository.save(fe1);

        QualityIndicator fe2 = new QualityIndicator();
        fe2.setName("Fe");
        fe2.setMeasurementUnit("ug/L");
        fe2.setAccredited(AccreditedStatus.YES);
        indicatorRepository.save(fe2);

        QualityIndicator fe3 = new QualityIndicator();
        fe3.setName("Fe");
        fe3.setMeasurementUnit("mg/L");
        fe3.setAccredited(AccreditedStatus.NO);
        indicatorRepository.save(fe3);

        QualityIndicator fe4 = new QualityIndicator();
        fe4.setName("Fe");
        fe4.setMeasurementUnit("ug/L");
        fe4.setAccredited(AccreditedStatus.NO);
        indicatorRepository.save(fe4);

        QualityIndicator cco = new QualityIndicator();
        cco.setName("CCO-Cr");
        cco.setMeasurementUnit("mg/L");
        cco.setAccredited(AccreditedStatus.YES);
        indicatorRepository.save(cco);

        Results r1 = new Results();
        r1.setSampleId(1L);
        r1.setReportId(1L);
        r1.setQualityIndicatorId(1L);
        r1.setResult("360");
        resultsRepository.save(r1);

        Results r2 = new Results();
        r2.setSampleId(1L);
        r2.setReportId(1L);
        r2.setQualityIndicatorId(5L);
        r2.setResult("<30");
        resultsRepository.save(r2);

        Results r3 = new Results();
        r3.setSampleId(2L);
        r3.setReportId(2L);
        r3.setQualityIndicatorId(2L);
        r3.setResult("<3");
        resultsRepository.save(r3);

        Results r4 = new Results();
        r4.setSampleId(3L);
        r4.setReportId(3L);
        r4.setQualityIndicatorId(5L);
        r4.setResult("766");
        resultsRepository.save(r4);

        Results r5 = new Results();
        r5.setSampleId(4L);
        r5.setReportId(4L);
        r5.setQualityIndicatorId(3L);
        r5.setResult("0.68");
        resultsRepository.save(r5);
    }
}
