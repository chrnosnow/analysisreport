package com.example.analysisreport.result.repository;

import com.example.analysisreport.result.controller.ResultsSearchDTO;
import com.example.analysisreport.result.entity.AnalysisResult;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomResultsRepositoryImpl implements CustomResultsRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AnalysisResult> customFind(ResultsSearchDTO searchRequest) {
        StringBuilder stringBuilder = new StringBuilder();
        Long sampleId = searchRequest.getSampleId();
        Long reportId = searchRequest.getReportId();
        String qualityIndicatorName = searchRequest.getQualityIndicatorName();

        if (sampleId != null && qualityIndicatorName == null) {
            stringBuilder.append("SELECT res.* FROM results res WHERE res.sample_id = " + sampleId);
        } else if (reportId != null && qualityIndicatorName == null) {
            stringBuilder.append("SELECT res.* FROM results res WHERE res.report_id = " + reportId);
        }

        if (qualityIndicatorName != null && sampleId != null) {
            stringBuilder.append("SELECT res.* FROM results res INNER JOIN water_quality_indicators ind ON res" +
                    ".quality_indicator_id = ind.id WHERE ind.name = '" + qualityIndicatorName + "' AND res.sample_id" +
                    " = " + sampleId);
        } else if (qualityIndicatorName != null && reportId != null) {
            stringBuilder.append("SELECT res.* FROM results res INNER JOIN water_quality_indicators ind ON res" +
                    ".quality_indicator_id = ind.id WHERE ind.name = '" + qualityIndicatorName + "' AND res.report_id" +
                    " = " + reportId);
        } else {
            stringBuilder.append("SELECT res.* FROM results res INNER JOIN water_quality_indicators ind ON res" +
                    ".quality_indicator_id = ind.id WHERE ind.name = '" + qualityIndicatorName + "'");
        }

        String query = stringBuilder.toString();
        Query namedQuery = entityManager.createNativeQuery(query);

        List<AnalysisResult> analysisResultList = new ArrayList<>();
        List<Object[]> list = namedQuery.getResultList();
        for (Object[] obj : list) {
            AnalysisResult result = new AnalysisResult();
            result.setId((Long) obj[0]);
            result.setSampleId((Long) obj[1]);
            result.setReportId((Long) obj[2]);
            result.setQualityIndicatorId((Long) obj[3]);
            result.setResult(String.valueOf(obj[4]));

            analysisResultList.add(result);
        }

        return analysisResultList;

    }
}
