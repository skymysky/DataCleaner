package org.eobjects.datacleaner.windows;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import org.eobjects.analyzer.configuration.AnalyzerBeansConfiguration;
import org.eobjects.analyzer.job.concurrent.MultiThreadedTaskRunner;
import org.eobjects.analyzer.job.concurrent.TaskRunner;
import org.eobjects.analyzer.storage.SqlDatabaseStorageProvider;
import org.eobjects.analyzer.storage.StorageProvider;
import org.eobjects.datacleaner.panels.DCBannerPanel;
import org.eobjects.datacleaner.panels.DCPanel;
import org.eobjects.datacleaner.util.ImageManager;
import org.eobjects.datacleaner.util.WidgetUtils;
import org.eobjects.datacleaner.widgets.HelpIcon;
import org.eobjects.datacleaner.widgets.label.MultiLineLabel;
import org.eobjects.datacleaner.widgets.tabs.CloseableTabbedPane;

public class OptionsDialog extends AbstractWindow {

	private static final long serialVersionUID = 1L;

	private final ImageManager imageManager = ImageManager.getInstance();
	private final CloseableTabbedPane _tabbedPane;
	private final AnalyzerBeansConfiguration _configuration;

	public OptionsDialog(AnalyzerBeansConfiguration configuration) {
		_configuration = configuration;
		_tabbedPane = new CloseableTabbedPane();

		_tabbedPane.addTab("General", imageManager.getImageIcon("images/menu/options.png"), getGeneralTab());
		_tabbedPane.addTab("Database drivers", imageManager.getImageIcon("images/model/datastore.png"),
				getDatabaseDriversTab());
		_tabbedPane.addTab("Network", imageManager.getImageIcon("images/menu/network.png"), getNetworkTab());
		_tabbedPane.addTab("Performance", imageManager.getImageIcon("images/menu/performance.png"), getPerformanceTab());

		_tabbedPane.setUnclosableTab(0);
		_tabbedPane.setUnclosableTab(1);
		_tabbedPane.setUnclosableTab(2);
		_tabbedPane.setUnclosableTab(3);
	}

	private DCPanel getGeneralTab() {
		DCPanel panel = new DCPanel();
		// TODO
		panel.add(new JLabel("TODO"));
		return panel;
	}

	private DCPanel getDatabaseDriversTab() {
		DCPanel panel = new DCPanel();
		// TODO
		panel.add(new JLabel("TODO"));
		return panel;
	}

	private DCPanel getNetworkTab() {
		DCPanel panel = new DCPanel();
		// TODO
		panel.add(new JLabel("TODO"));
		return panel;
	}

	private DCPanel getPerformanceTab() {
		DCPanel panel = new DCPanel();

		int row = 0;

		TaskRunner taskRunner = _configuration.getTaskRunner();
		WidgetUtils.addToGridBag(new JLabel("Task runner type:"), panel, 0, row);
		WidgetUtils.addToGridBag(new JLabel(taskRunner.getClass().getSimpleName()), panel, 1, row);
		WidgetUtils
				.addToGridBag(
						new HelpIcon(
								"The task runner is used to determine the execution strategy of Analysis jobs. The most common strategy for this is to use a multithreaded task runner which will spawn several threads to enable concurrent execution of jobs."),
						panel, 2, row);

		if (taskRunner instanceof MultiThreadedTaskRunner) {
			int numThreads = ((MultiThreadedTaskRunner) taskRunner).getNumThreads();

			if (numThreads > 0) {
				row++;
				WidgetUtils.addToGridBag(new JLabel("Thread pool size:"), panel, 0, row);
				WidgetUtils.addToGridBag(new JLabel("" + numThreads), panel, 1, row);
			}
		}

		row++;
		StorageProvider storageProvider = _configuration.getStorageProvider();
		WidgetUtils.addToGridBag(new JLabel("Storage provider type:"), panel, 0, row);
		WidgetUtils.addToGridBag(new JLabel(storageProvider.getClass().getSimpleName()), panel, 1, row);
		WidgetUtils
				.addToGridBag(
						new HelpIcon(
								"The storage provider is used for staging data during and after analysis, typically to store the results on disk in stead of holding everything in memory."),
						panel, 2, row);

		if (storageProvider instanceof SqlDatabaseStorageProvider) {
			int inMemoryThreshold = ((SqlDatabaseStorageProvider) storageProvider).getInMemoryThreshold();
			row++;
			WidgetUtils.addToGridBag(new JLabel("Max rows kept in memory:"), panel, 0, row);
			WidgetUtils.addToGridBag(new JLabel("" + inMemoryThreshold), panel, 1, row);
		}

		row++;
		MultiLineLabel descriptionLabel = new MultiLineLabel(
				"<html>Performance options are currently not configurable while you're running the application. "
						+ "You need to edit the applications configuration file for this. The configuration file is named "
						+ "<b>conf.xml</b> and is located in the root of the folder where you've installed DataCleaner.</html>");
		descriptionLabel.setBorder(new EmptyBorder(10, 10, 0, 10));
		WidgetUtils.addToGridBag(descriptionLabel, panel, 0, row, 2, 1);
		return panel;
	}

	@Override
	protected JComponent getWindowContent() {
		DCPanel panel = new DCPanel(WidgetUtils.BG_COLOR_DARK, WidgetUtils.BG_COLOR_DARK);
		panel.setLayout(new BorderLayout());
		panel.add(new DCBannerPanel("Options"), BorderLayout.NORTH);
		panel.add(_tabbedPane, BorderLayout.CENTER);

		panel.setPreferredSize(500, 500);
		return panel;
	}

	@Override
	protected boolean isWindowResizable() {
		return true;
	}

	@Override
	protected boolean isCentered() {
		return true;
	}

	@Override
	protected String getWindowTitle() {
		return "Options";
	}

	@Override
	protected Image getWindowIcon() {
		return imageManager.getImage("images/menu/options.png");
	}

}
