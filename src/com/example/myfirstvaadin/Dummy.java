package com.example.myfirstvaadin;

import com.github.wolfie.refresher.Refresher;

public class Dummy {
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
