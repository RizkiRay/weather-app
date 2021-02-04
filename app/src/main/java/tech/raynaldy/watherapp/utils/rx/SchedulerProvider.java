package tech.raynaldy.watherapp.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by ray <rizkirayraynaldy@gmail.com> on 2/3/21.
 */

public interface SchedulerProvider {
    Scheduler ui();
    Scheduler computation();
    Scheduler io();
}
