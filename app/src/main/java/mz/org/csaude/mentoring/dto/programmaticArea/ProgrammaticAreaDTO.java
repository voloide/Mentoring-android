package mz.org.csaude.mentoring.dto.programmaticArea;

import lombok.Data;
import lombok.NoArgsConstructor;
import mz.org.csaude.mentoring.base.dto.BaseEntityDTO;
import mz.org.csaude.mentoring.dto.program.ProgramDTO;
import mz.org.csaude.mentoring.model.programmaticArea.ProgrammaticArea;
@Data
@NoArgsConstructor
public class ProgrammaticAreaDTO extends BaseEntityDTO {
    private String code;
    private String description;
    private String name;
    private ProgramDTO program;
    public ProgrammaticAreaDTO(ProgrammaticArea programmaticArea) {
        super(programmaticArea);
        this.setCode(programmaticArea.getCode());
        this.setDescription(programmaticArea.getDescription());
        this.setName(programmaticArea.getName());
        if(programmaticArea.getProgram()!=null) {
            this.setProgram(new ProgramDTO(programmaticArea.getProgram()));
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProgramDTO getProgram() {
        return program;
    }

    public void setProgram(ProgramDTO program) {
        this.program = program;
    }

    public ProgrammaticArea getProgrammaticArea() {
        ProgrammaticArea programmaticArea = new ProgrammaticArea();
        programmaticArea.setCode(this.getCode());
        programmaticArea.setDescription(this.getDescription());
        programmaticArea.setName(this.getName());
        programmaticArea.setUuid(this.getUuid());
        programmaticArea.setCreatedAt(this.getCreatedAt());
        programmaticArea.setUpdatedAt(this.getUpdatedAt());
        if(this.getProgram()!=null) {
            programmaticArea.setProgram(this.program.getProgram());
        }
        return programmaticArea;
    }
}
