package edu.kit.informatik.timeRecordingSystem.typesOfEmployees;

import edu.kit.informatik.timeRecordingSystem.Holidays;
import edu.kit.informatik.timeRecordingSystem.Name;
import edu.kit.informatik.timeRecordingSystem.WorkDay;
import edu.kit.informatik.timeRecordingSystem.time.Date;

/**
 * Models a night production employee extending the type Employee. A night production employee
 * can therefore access all methods from Employee. Night production employees are allowed to
 * work on Sundays and holidays. They are also allowed to cover night shifts.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class NPEmployee extends Employee {
    
    /**
     * Defines if the employee is allowed to work everyday including Sunday.
     */
    private static final boolean CAN_WORK_EVERYDAY = true;
    
    /**
     * Defines if the employee is allowed to cover night shifts.
     */
    private static final boolean CAN_NIGHT_SHIFT = true;
    
    /**
     * Constructor for a night production employee. Initializes its attributes.
     * @param name The employees name formed by first and last name.
     * @param birthday The employees date of birth.
     * @param holidays The employees holidays.
     * @param type The type of employee.
     */
    public NPEmployee(Name name, Date birthday, Holidays holidays, String type) {
        super(name, birthday, holidays, type);
        canWorkEveryday = CAN_WORK_EVERYDAY;
        canNightShift = CAN_NIGHT_SHIFT;
    }

    /**
     * Overrides canWork(WorkDay). A night production employee is allowed to work
     * on Sundays, holidays and cover night shifts.
     * 
     * Returns always true as the basic criteria is always covered.
     */
    @Override
    public boolean canWork(WorkDay workDay) {
        
        return true;
    }

    /**
     * Overrides reasonCantWork(WorkDay). Night production workers always
     * cover all the basic criteria for being able to work. Therefore this
     * method always returns null.
     */
    @Override
    public String reasonCantWork(WorkDay workDay) {
        
        return null;
    }
}