package com.example.myfirstvaadin;

import com.github.wolfie.refresher.Refresher;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.PlotOptionsBar;
import com.vaadin.addon.charts.model.XAxis;
import com.vaadin.addon.charts.model.YAxis;

public class Dummy {
//	
//	// Chart testing section
//	Chart testChart = new Chart(ChartType.BAR);
//	testChart.setWidth("300px");
//	testChart.setHeight("300px");
//	Configuration conf = testChart.getConfiguration();
//	conf.setTitle("Planets");
//	conf.setSubTitle("This is the subtitle");
//	conf.getLegend().setEnabled(false);
//	// Disable markers form lines
//	PlotOptionsBar plotOptions = new PlotOptionsBar();
//	// plotOptions.setMarker(new Marker(false));
//	conf.setPlotOptions(plotOptions);
//
//	// The data
//	ListSeries series = new ListSeries("Diameter");
//	series.setData(4900, 12100, 12800, 6800, 143000, 125000, 51100, 49500);
//	conf.addSeries(series);
//
//	// The X axis
//	XAxis x = new XAxis();
//	x.setCategories("Mercury", "Venus", "Earth", "mars", "Jupiter", "Saturn", "Uranus", "Neptune");
//	x.setTitle("Planet name ");
//	conf.addxAxis(x);
//
//	// The Y Axis
//	YAxis y = new YAxis();
//	y.setTitle("Diameter Value");
//	// y.getLabels().setfor;
//	// y.getLabels().setStep(2);
//	conf.addyAxis(y);
//
//	gridLayout.addComponent(testChart);
//	
//	setContent(layout);
	
//	Grid grid = new Grid();
//	BeanItemContainer container = createBeanContainer();
//	container = createSQLContainer();
//	grid.setContainerDataSource(container);
//	grid.addColumn("col 001");
//	grid.addColumn("col 002");
//	grid.addRow("John Smith", "301-222-5566");
//	grid.addRow("Methuw Olney", "240-444-5566");
	
//	layout.addComponent(grid);
	
	// Start the data feed thread
//	new FeederThread().start();
//	grid.setCaption("Double Click to edit");
//	grid.setSizeFull();
//	grid.setEditorEnabled(true);
//	grid.setSelectionMode(SelectionMode.NONE);
//	
//	grid.addColumn("index", Integer.class)
//			.setRenderer(new NumberRenderer("%02d")).setHeaderCaption("##")
//			.setExpandRatio(0).setEditable(false).setWidth(50);
//	
//	grid.addColumn("name", String.class)
//			.setRenderer(new TextRenderer()).setExpandRatio(2); // could not find BoldLastNameRenderer class
//	
//	Slider progressEditor = new Slider();
//	progressEditor.setWidth(100.0f, Unit.PERCENTAGE);
//	progressEditor.setMax(150.0);
//	progressEditor.setMin(1.0);
//	grid.addColumn("progress", Double.class)
//			.setRenderer(new ProgressBarRenderer() {
//				@Override
//				public JsonValue encode(Double value) {
//					if(value != null){
//						value = (value -1 )/149.0;
//					}
//					return super.encode(value);
//				}
//				
//			}).setEditorField(progressEditor).setExpandRatio(2);
	
	/*grid.addColumn("weight", Double.class)
			.setRenderer(new NumberRenderer());*/
	
//	Random r = new Random();
//	for (int i = 0; i < 10; ++i){
//		
//		String name = "John Smith";
//		
//		grid.addRow(i, 
//				name,
//				Math.sin(i/3.0)*75 + 74
//				);
//	}
	
//	layout.addComponent(grid);
	
	
	/*final VerticalLayout layout = new VerticalLayout();
	layout.setMargin(true);
	
	
	setContent(layout);

	Button button = new Button("Click Me");
	button.addClickListener(new Button.ClickListener() {
		public void buttonClick(ClickEvent event) {
			layout.addComponent(new Label("Thank you for clicking"));
		}
	});
	layout.addComponent(button);
	
	Link link = new Link("Google search", new ExternalResource("http://www.google.com"));
	link.setDescription("Visit google.com");
	layout.addComponent(link);   */

//    private void startUIUpdate()
//    {
//        Refresher refresher = new Refresher();
//        refresher.setRefreshInterval(4000);
//        refresher.addListener(new Refresher.RefreshListener() {
//            @Override
//            public void refresh(Refresher refresher) {
//                updateGauges();
//            }
//        });
//
//        addExtension(refresher);
//    }

}
