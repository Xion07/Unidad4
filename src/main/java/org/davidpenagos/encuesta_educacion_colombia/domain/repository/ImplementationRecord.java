package org.davidpenagos.encuesta_educacion_colombia.domain.repository;

import org.davidpenagos.encuesta_educacion_colombia.domain.model.Survey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Collections;
import java.io.IOException;
public class ImplementationRecord implements MethodRecord {

    private static final Logger logger = LoggerFactory.getLogger( ImplementationRecord.class);
    private final List<Survey> surveyList;

    public ImplementationRecord() {
        this.surveyList = new ArrayList<>(loadSurvey());//Al momento de construir el Repository se cargan los datos desde el archivo
    }

    private List<Survey> loadSurvey(){
        logger.info( "Cargando los datos desde archivo" );
        List<String> plainTextGasEmissonsList =  readFileWithGrades();
        return plainTextGasEmissonsList.stream().map( this::buildSurvey ).toList();
    }

    private List<String> readFileWithGrades(){


        Path path = Paths.get( "./src/main/resources/Survey.txt");
        try (Stream<String> stream = Files.lines( path)) {
            return stream.toList();
        } catch (IOException x) {
            logger.error("IOException: {0}", x);
        }
        return Collections.emptyList();
    }

    private Survey buildSurvey(String plainTextGrade){


        String[] questionArray = plainTextGrade.split(",");

        return new Survey( questionArray[0],
                                             Double.valueOf(questionArray[1]),
                                             String.valueOf(questionArray[2]),
                                             String.valueOf(questionArray[3]),
                                             Double.valueOf(questionArray[4]),
                                             Double.valueOf(questionArray[5]));
    }

    @Override
    public List<Survey> dataSurvey() { return surveyList; }
}