package edu.kit.informatik.timeRecordingSystem.typesOfEmployees;

import edu.kit.informatik.timeRecordingSystem.Holidays;
import edu.kit.informatik.timeRecordingSystem.Main;
import edu.kit.informatik.timeRecordingSystem.Name;
import edu.kit.informatik.timeRecordingSystem.Shift;
import edu.kit.informatik.timeRecordingSystem.WorkDay;
import edu.kit.informatik.timeRecordingSystem.time.Date;

/**
 * Models a night employee extending the type Employee. A night employee can therefore
 * access all methods from Employee. Night employees are not allowed to work on Sundays or
 * holidays.
 * 
 * @author Emilio Rivera
 * @version 1.0
 *
 */
public class NEmployee extends Employee {
    
    /**
     * Defines if the employee is allowed to work everyday including Sunday.
     */
    private static final boolean CAN_WORK_EVERYDAY = false;
    
    /**
     * Defines if the employee is allowed to cover night shifts.
     */
    private static final boolean CAN_NIGHT_SHIFT = true;
    
    /**
     * Constructor for a night employee. Initializes its attributes.
     * @param name The employees name formed by first and last name.
     * @param birthday The employees date of birth.
     * @param holidays The employees holidays.
     * @param type The type of the employee.
     */
    public NEmployee(Name name, Date birthday, Holidays holidays, String type) {
        super(name, birthday, holidays, type);
        canWorkEveryday = CAN_WORK_EVERYDAY;
        canNightShift = CAN_NIGHT_SHIFT;
    }

    /**
     * Overrides canWork(WorkDay). A night employee can't work on a
     * Sunday or Holiday.
     * 
     * Returns true if the night employee is allowed to work.
     */
    @Override
    public boolean canWork(WorkDay workDay) {
        
        return !workDay.isSunday() && !workDay.isHoliday(getHolidays());
    }

    /**
     * Overrides reasonCantWork(WorkDay). Checks the reason for the employee not
     * being able to work. Returns an error explaining the reason.
     */
    @Override
    public String reasonCantWork(WorkDay workDay) {
        if (workDay.isSunday()) {
            return Main.ERROR + Shift.NO_SUNDAY_WORK;
        } else {
            return Main.ERROR + Shift.NO_HOLIDAY_WORK;
        }
    }
    
}