package myqueueserver.MachineStatus;

import java.lang.management.OperatingSystemMXBean;

/**
 *
 * @author Nikos Siatras
 */
public class MachineStatus
{

    // Status
    public static int fAvailableProcessors = 0;
    public static long fCPULoad = 0;                // The system's CPU Load %
    public static long fFreeMemory = 0;             // The systems's Free Memory in Megabytes
    public static long fTotalMemory = 0;            // The system's Total Memory in Megabytes
    /////////////////
    private static Thread fUpdateStatusThread;

    public MachineStatus()
    {
    }

    public static void Initialize()
    {
        fUpdateStatusThread = new Thread(new Runnable()
        {

            @Override
            public void run()
            {
                while (true)
                {
                    com.sun.management.OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) java.lang.management.ManagementFactory.getOperatingSystemMXBean();
                    fTotalMemory = (long) (bean.getTotalPhysicalMemorySize() * 0.000976562 * 0.0009765625);
                    fFreeMemory = (long) (bean.getFreePhysicalMemorySize() * 0.000976562 * 0.0009765625);
                    fCPULoad = Math.round(bean.getSystemCpuLoad() * 100);

                    try
                    {
                        Thread.sleep(5000);
                    }
                    catch (InterruptedException ex)
                    {
                    }
                }
            }
        });


        fUpdateStatusThread.start();
    }
}
