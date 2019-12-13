package com.devonfw.java.modules.swttest;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.waits.DefaultCondition;
import org.eclipse.swtbot.swt.finder.waits.ICondition;

/**
 * Condition for {@link SWTBot#waitUntil(ICondition)} to wait until all Jobs are finished.
 */
public class AllJobsAreFinished extends DefaultCondition {

  @Override
  public boolean test() throws Exception {

    return Job.getJobManager().isIdle();
  }

  @Override
  public String getFailureMessage() {

    return "Could not finish all Jobs in the given amount of time.";
  }

}
