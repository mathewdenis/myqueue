package myqueueserver.MachineStatus;

import java.lang.management.OperatingSystemMXBean;

/**
 *
 * @author Nikos Siatras
 */
public class MachineStatus
{

    private static com.sun.management.OperatingSystemMXBean fBean = (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();

    public MachineStatus()
    {
    }

    public static void Initialize()
    {
    }

    /**
     * The system's CPU Load %
     *
     * @return
     */
    public static long getCPULoad()
    {
        return Math.round(fBean.getSystemCpuLoad() * 100);
    }

    /**
     * The system's Free Memory in Megabytes
     *
     * @return
     */
    public static long getFreeMemory()
    {
        return (long) (fBean.getFreePhysicalMemorySize() * 0.000976562 * 0.0009765625);
    }

    /**
     * The system's Total Memory in Megabytes
     *
     * @return
     */
    public static long getTotalMemory()
    {
        return (long) (fBean.getTotalPhysicalMemorySize() * 0.000976562 * 0.0009765625);
    }
}
