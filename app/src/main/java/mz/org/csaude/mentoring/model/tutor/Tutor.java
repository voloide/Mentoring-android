package mz.org.csaude.mentoring.model.tutor;

import static mz.org.csaude.mentoring.model.tutor.Tutor.COLUMN_TABLE_NAME;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import mz.org.csaude.mentoring.base.model.BaseModel;
import mz.org.csaude.mentoring.dao.tutor.TutorDAOImpl;
import mz.org.csaude.mentoring.dto.tutor.TutorDTO;
import mz.org.csaude.mentoring.model.employee.Employee;

@DatabaseTable(tableName = COLUMN_TABLE_NAME, daoClass = TutorDAOImpl.class)
public class Tutor extends BaseModel {

  public static final String COLUMN_TABLE_NAME = "tutor";
  public static final String COLUMN_EMPLOYEE= "employee_id";

  @DatabaseField(columnName = COLUMN_EMPLOYEE, canBeNull = false, foreign = true, foreignAutoRefresh = true)
  private Employee employee;

  public Tutor() {
  }

  public Tutor(TutorDTO tutorDTO) {
    super(tutorDTO);
    this.setEmployee(new Employee(tutorDTO.getEmployeeDTO()));
  }
  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  @Override
  public String validade() {
    return this.employee.validade();
  }
}
