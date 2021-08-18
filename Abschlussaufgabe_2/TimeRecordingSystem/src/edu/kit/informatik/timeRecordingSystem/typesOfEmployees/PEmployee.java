package edu.kit.informatik.timeRecordingSystem.typesOfEmployees;

import edu.kit.informatik.timeRecordingSystem.Holidays;
import edu.kit.informatik.timeRecordingSystem.Main;
import edu.kit.informatik.timeRecordingSystem.Name;
import edu.kit.informatik.timeRecordingSystem.WorkDay;
import edu.kit.informatik.timeRecordingSystem.shifts.NightShift;
import edu.kit.informatik.timeRecordingSystem.time.Date;

/**
 * Models a production employee extending the type Employee. A production employee can therefore
 * access all methods from Employee. Production employees are allowed to work on Sundays and on
 * Holidays but can cover night shifts.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class PEmployee extends Employee {
    
    /**
     * Defines if the employee is allowed to work everyday including Sunday.
     */
    private static final boolean CAN_WORK_EVERYDAY = true;
    
    /**
     * Defines if the employee is allowed to cover night shifts.
     */
    private static final boolean CAN_NIGHT_SHIFT = false;
    
    /**
     * Constructor for a production employee. Initializes its attributes.
     * @param name The employees Name formed by first and last name.
     * @param birthday The employees date of birth.
     * @param holidays The employees holidays.
     * @param type The type of employee.
     */
    public PEmployee(Name name, Date birthday, Holidays holidays, String type) {
        super(name, birthday, holidays, type);
        canWorkEveryday = CAN_WORK_EVERYDAY;
        canNightShift = CAN_NIGHT_SHIFT;
    }

    /**
     * Overrides canWork(WorkDay). A production employee is allowed to work
     * on Sundays and holidays but can't cover night shifts.
     */
    @Override
    public boolean canWork(WorkDay workDay) {
        
        return !workDay.isNightShift();
    }

    /**
     * Overrides reasonCantWork(WorkDay). Checks the reason for the employee not
     * being able to work. Returns an error explaining the reason.
     */
    @Override
    public String reasonCantWork(WorkDay workDay) {
        
        return Main.ERROR + NightShift.NO_NIGHT_SHIFT;
    }
}