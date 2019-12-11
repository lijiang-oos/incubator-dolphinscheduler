/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dolphinscheduler.dao.entity;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.apache.dolphinscheduler.common.enums.*;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
public class ProcessInstance {
    private int id;
    private int processDefinitionId;
    private ExecutionStatus state;
    private Flag recovery;
    private Date startTime;
    private Date endTime;
    private int runTimes;
    private String name;
    private String host;
    private ProcessDefinition processDefinition;
    private CommandType commandType;
    private String commandParam;
    private TaskDependType taskDependType;
    private int maxTryTimes;
    private FailureStrategy failureStrategy;
    private WarningType warningType;
    private Integer warningGroupId;
    private Date scheduleTime;
    private Date commandStartTime;
    private String globalParams;
    private String processInstanceJson;
    private int executorId;
    private String tenantCode;
    private String queue;
    private Flag isSubProcess;
    private String locations;
    private String connects;
    private String historyCmd;
    private String dependenceScheduleTimes;
    private Long duration;
    private Priority processInstancePriority;
    private int workerGroupId;
    private int timeout;
    private int tenantId;
    private String workerGroupName;
    private String receivers;
    private String receiversCc;

    /**
     * set the process name with process define version and timestamp
     *
     * @param processDefinition processDefinition
     */
    public ProcessInstance(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
        this.name = processDefinition.getName() + "-" +
                processDefinition.getVersion() + "-" +
                System.currentTimeMillis();
    }

    public ProcessDefinition getProcessDefinition() {
        return processDefinition;
    }

    public void setProcessDefinition(ProcessDefinition processDefinition) {
        this.processDefinition = processDefinition;
    }

    /**
     * add command to history
     *
     * @param cmd cmd
     */
    public void addHistoryCmd(CommandType cmd) {
        if (StringUtils.isNotEmpty(this.historyCmd)) {
            this.historyCmd = String.format("%s,%s", this.historyCmd, cmd.toString());
        } else {
            this.historyCmd = cmd.toString();
        }
    }

    /**
     * check this process is start complement data
     *
     * @return whether complement data
     */
    public Boolean isComplementData() {
        if (!StringUtils.isNotEmpty(this.historyCmd)) {
            return false;
        }
        return historyCmd.startsWith(CommandType.COMPLEMENT_DATA.toString());
    }

    /**
     * get current command type,
     * if start with complement data,return complement
     *
     * @return CommandType
     */
    public CommandType getCmdTypeIfComplement() {
        if (isComplementData()) {
            return CommandType.COMPLEMENT_DATA;
        }
        return commandType;
    }
}
