package Java_concurrency_in_practice.chap6;

import java.util.*;
import java.util.concurrent.*;

/**
 * quote 报价获取与展示
 * 利用 ExecutorService#invokeAll 实现按照"任务集合"的顺序把 Future 添加到"结果集合"中
 *
 * @author jianghao.zhang
 */
public class QuoteShow {
    /**
     * 以 CPU 的核心数量作为线程池固定大小
     * <p>
     */
    private final ExecutorService es = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public List<TravelQuote> getRankedTravelQuotes(TravelLine travelLine, Set<TravelCompany> companySet,
                                                   Comparator<TravelQuote> ranking, long time, TimeUnit unit) {
        // 计算任务集合
        List<QuoteTask> tasks = new ArrayList<>();
        for (TravelCompany company : companySet) {
            tasks.add(new QuoteTask(company, travelLine));
        }

        // 计算结果集合
        List<Future<TravelQuote>> futures = new ArrayList<>();
        try {
            // 把所有爆竹的引线一起点燃
            // 注意！任务集合的迭代顺序和结果集合的迭代顺序完全一样，注意结果
            futures = es.invokeAll(tasks, time, unit);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<TravelQuote> quotes = new ArrayList<>(tasks.size());
        Iterator<QuoteTask> taskIterator = tasks.iterator();
        for (Future<TravelQuote> f : futures) {
            // 任务集合和结果集合同步迭代
            QuoteTask task = taskIterator.next();
            try {
                // 取一个计算结果，填入报价list
                quotes.add(f.get());
            } catch (InterruptedException e) {
                quotes.add(task.getFailureQuote());
            } catch (ExecutionException e) {
                quotes.add(task.getTimeoutQuote());
            }
        }

        // 排序报价
        quotes.sort(ranking);
        return quotes;
    }
}

/**
 * 计算报价的任务类
 */
class QuoteTask implements Callable<TravelQuote> {
    private final TravelCompany travelCompany;
    private final TravelLine travelLine;

    public QuoteTask(TravelCompany travelCompany, TravelLine travelLine) {
        this.travelCompany = travelCompany;
        this.travelLine = travelLine;
    }

    /**
     * 线程计算任务具体实现
     *
     * @return 报价信息
     */
    @Override
    public TravelQuote call() {
        // 根据旅游公司品牌和旅游路线计算报价，这里图省事直接用随机数了
        System.out.println(travelCompany.toString() + travelLine.toString());
        return new TravelQuote(new Random().nextLong());
    }

    public TravelQuote getFailureQuote() {
        return new TravelQuote(-1L);
    }

    public TravelQuote getTimeoutQuote() {
        return new TravelQuote(-1L);
    }
}

/**
 * 旅游公司信息
 */
class TravelCompany {
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }
}

/**
 * 旅游线路信息
 */
class TravelLine {
}

/**
 * 旅游公司线路对应报价信息
 */
class TravelQuote {
    private Long price;

    public TravelQuote(Long price) {
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }
}
