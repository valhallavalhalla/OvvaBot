package com.bot.service;

import com.bot.model.ovvatv.Program;
import com.bot.model.ovvatv.ProgramsData;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class MessageBuilderService {

    public String buildTvProgram(ProgramsData programsData) {
        StringBuilder programBuilder = new StringBuilder();

        for(Program program : programsData.getData().getPrograms()) {
            Date date = new Timestamp(program.getRealtimeBegin());
            Instant instant = Instant.ofEpochSecond(date.getTime());
            LocalTime res = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
            String programTimeStart = res.toString();
            programBuilder.append(programTimeStart + "\t" + program.getTitle() + "\n");
        }

        return programBuilder.toString();
    }
}
