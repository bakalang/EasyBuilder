package net.skyee.schedulers;

import de.spinscale.dropwizard.jobs.Job;
import net.skyee.Context;
import net.skyee.bean.Login;
import net.skyee.svn.SvnRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//@On("0/15 * * * * ?")
public class DailyStrongTransactionScheduler extends Job {
    Logger log = LoggerFactory.getLogger(DailyStrongTransactionScheduler.class);

    public static final String URL="http://jsjustweb.jihsun.com.tw/z/zg/zg_F_0_1.djhtm";

    private Context context;

    public DailyStrongTransactionScheduler() {
        context= Context.getInstance();
    }

    @Override
    public void doJob() {
//        System.out.println("!!");
//        SvnRepository d = new SvnRepository();
//        try {
//            d.test("servicegatewayapi");
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

}