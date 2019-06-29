package com.example.terencepan.spring.batch.reclassetl.writers;

import com.example.terencepan.spring.batch.reclassetl.model.ReclassItem;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static com.example.terencepan.spring.batch.reclassetl.tasks.ReclassTasks.*;

public class ReclassWriter implements
        Tasklet, StepExecutionListener {

    private final Logger logger = LoggerFactory
            .getLogger(ReclassWriter.class);

    private String fileName;
    private CSVWriter CSVWriter;
    private FileWriter fileWriter;
    private File file;

    private void initWriter() throws Exception {
        if (file == null) {
            file = new File(fileName);
            file.createNewFile();
        }
        if (fileWriter == null) fileWriter = new FileWriter(file, true);
        if (CSVWriter == null) CSVWriter = new CSVWriter(fileWriter);
    }

    public void writeLine(ReclassItem reclassItem) throws Exception {
        if (CSVWriter == null)
            initWriter();
        String[] lineStr = new String[2];
        lineStr[0] = reclassItem.getArRootDocument();
        CSVWriter.writeNext(lineStr);
    }


    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        /*for (Line line : lines) {
            fu.writeLine(line);
            logger.debug("Wrote line " + line.toString());
        }
        */
        //prepareReclassEventCarsPacket(prepareCarsReclassEvent(reclassItem));
        //sendReclassCarsPacket(prepareReclassEventCarsPacket(prepareCarsReclassEvent(reclassItem)));
        return RepeatStatus.FINISHED;
    }
    public void closeWriter() {
        try {
            CSVWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            logger.error("Error while closing writer.");
        }
    }

    private List<ReclassItem> reclassItemList;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext executionContext = stepExecution
                .getJobExecution()
                .getExecutionContext();

    }
    /*
    @Override
    public void write(List<? extends ReclassItem> reclassItems) throws Exception {

        logger.debug("Wrote reclass for Invoice #: ");
    }*/

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        closeWriter();
        logger.debug("Reclass Writer finished.");
        return ExitStatus.COMPLETED;
    }

}
