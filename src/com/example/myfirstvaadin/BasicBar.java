package com.example.myfirstvaadin;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.HorizontalAlign;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.LayoutDirection;
import com.vaadin.addon.charts.model.Legend;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsBar;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.addon.charts.model.VerticalAlign;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.ui.Component;

public class BasicBar {
	
	public String getDescription(){
		return "Basic Bar";
	}

	public Component getChart(){
		
		Chart chart = new Chart(ChartType.BAR);
		
		Configuration conf = new Configuration();
		
		conf.setTitle("Historic World Population By region");
		conf.setSubTitle("Source: Wikipedia.org");
		
		XAxis x = new XAxis();
		x.setCategories(new String[]{"Africa","America","Asia", "Europe", "Oceania"});
		// x.setTitle(new AxisTitle(new AxisTitle((String) null));
		conf.addxAxis(x);
		
		YAxis y = new YAxis();
		y.setMin(0);
		y.setTitle(new String("Population (millions)"));
		conf.addyAxis(y);
		
		Tooltip tooltip = new Tooltip();
		tooltip.setFormatter("this.series.name+': ' + this.y + 'millions'");
		conf.setTooltip(tooltip);
		
		
		// Plot option should be consistent with Charttype
		PlotOptionsBar plot = new PlotOptionsBar();
		Labels barLabel = new Labels();
		plot.setDataLabels(barLabel);
		conf.setPlotOptions(plot);

		// Legend - A Box describing the plot
		Legend legend = new Legend();
		legend.setLayout(LayoutDirection.VERTICAL);
		legend.setHorizontalAlign(HorizontalAlign.RIGHT);
		legend.setVerticalAlign(VerticalAlign.TOP);
		legend.setX(-100);
		legend.setY(100);
		legend.setFloating(true);
		legend.setBorderWidth(1);
		legend.setBackgroundColor(new SolidColor("#FFFFF"));
		legend.setShadow(true);
		conf.setLegend(legend);
		
		// Actually the following line is not necessary with license
		conf.disableCredits();
		
		// get data ready
		List series = new ArrayList<ListSeries>();
		series.add(new ListSeries("Year 1800", 107, 31, 635, 203, 2));
		series.add(new ListSeries("Year 1900", 133, 156, 947, 408, 6));
		series.add(new ListSeries("Year 2008", 973, 914, 4054, 732, 34));
		conf.setSeries(series);
		
		chart.drawChart(conf);
		return chart;
	}
	
	
}
