package UI;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import Data.*;
import Logging.cLoggingBase;

/**
 * Main JavaFX application for viewing StarViewer universe data.
 * Provides a tree view of the cosmic hierarchy with detailed information panel.
 * 
 * @author Stephen Hyberger
 * @version 1.0
 */
public class StarViewerApp extends Application 
{
    
    private TreeView<Object> treeView;
    private VBox detailPanel;
    private Label titleLabel;
    private TextArea propertiesArea;
    
    private cUniverse universe;

    /**
     * Main entry point for graphical application of StarViewer.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the JavaFX application. Does setup of UI components and event handlers.
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) 
    {
        primaryStage.setTitle("StarViewer");
        
        // Initialize the universe with test data
        InitializeUniverse();
        
        // Create the main layout
        SplitPane mainSplitPane = new SplitPane();
        mainSplitPane.setOrientation(Orientation.HORIZONTAL);
        
        // Create and setup the tree view (left side)
        treeView = new TreeView<>();
        treeView.setShowRoot(true);
        
        // Build the tree structure
        BuildTreeStructure();
        
        // Add selection listener
        treeView.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    DisplayObjectDetails(newValue.getValue());
                }
            }
        );
        
        // Create and setup the detail panel (right side)
        detailPanel = new VBox(10);
        detailPanel.setPrefWidth(500);
        detailPanel.setPadding(new javafx.geometry.Insets(10));
        
        // Title label
        titleLabel = new Label("Select an object to view details");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Properties area with scrollbar
        Label propsLabel = new Label("Properties:");
        propsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        propertiesArea = new TextArea();
        propertiesArea.setEditable(false);
        propertiesArea.setPrefHeight(300);
        propertiesArea.setWrapText(true);
        propertiesArea.setStyle("-fx-font-family: 'Courier New'; -fx-font-size: 12px;");
        
        // Make properties area grow to fill available space
        VBox.setVgrow(propertiesArea, Priority.ALWAYS);
        
        detailPanel.getChildren().addAll(
            titleLabel,
            new Separator(),
            propsLabel,
            propertiesArea
        );
        
        VBox treePanel = new VBox(10);
        
        // Title
        // Label treeTitle = new Label("Universe Structure");
        // treeTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        // Controls
        HBox controls = new HBox(10);
        Button refreshButton = new Button("Refresh");
        Button generateButton = new Button("Generate New Universe");
        
        refreshButton.setOnAction(e -> RefreshTreeView());
        generateButton.setOnAction(e -> GenerateNewUniverse());
        
        controls.getChildren().addAll(refreshButton, generateButton);
        
        treePanel.getChildren().addAll(controls, treeView);
        VBox.setVgrow(treeView, Priority.ALWAYS);

        // Add both sides to the split pane
        mainSplitPane.getItems().addAll(treePanel, detailPanel);
        mainSplitPane.setDividerPositions(0.4); // 40% for tree, 60% for details
        
        // Create menu bar
        MenuBar menuBar = new MenuBar();
        
        // File menu
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> System.exit(0));
        fileMenu.getItems().add(exitItem);
        
        // Universe menu
        Menu universeMenu = new Menu("Universe");
        MenuItem generateItem = new MenuItem("Generate New Universe");
        MenuItem testUniverseItem = new MenuItem("Load Test Universe");

        generateItem.setOnAction(e -> GenerateNewUniverse());
        testUniverseItem.setOnAction(e -> LoadTestUniverse());
        
        universeMenu.getItems().addAll(generateItem, testUniverseItem);
        
        menuBar.getMenus().addAll(fileMenu, universeMenu);
        
        // Main layout
        BorderPane root = new BorderPane();
        root.setTop(menuBar);
        root.setCenter(mainSplitPane);
        
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    /**
     * Initializes the universe with test data
     */
    private void InitializeUniverse() {
        universe = new cUniverse();
        universe.TestUniverse();
    }
    
    /**
     * Builds the tree structure from the universe data. Needs to be called at initialization and after any data changes.
     */
    private void BuildTreeStructure() {
        TreeItem<Object> rootItem = new TreeItem<>(universe);
        rootItem.setExpanded(true);
        
        // Add galaxies
        for (int i = 0; i < universe.GetGalaxyCount(); i++) {
            cGalaxy galaxy = universe.GetGalaxy(i);
            TreeItem<Object> galaxyItem = new TreeItem<>(galaxy);
            galaxyItem.setExpanded(false);
            
            // Add stars to this galaxy
            for (int j = 0; j < galaxy.GetStarCount(); j++) {
                cStar star = galaxy.GetStar(j);
                TreeItem<Object> starItem = new TreeItem<>(star);
                starItem.setExpanded(false);
                
                // Add planets to this star
                for (int k = 0; k < star.GetPlanetCount(); k++) {
                    cPlanet planet = star.GetPlanet(k);
                    TreeItem<Object> planetItem = new TreeItem<>(planet);
                    planetItem.setExpanded(false);
                    
                    // Add moons to this planet
                    for (int l = 0; l < planet.GetMoonCount(); l++) {
                        cPlanet moon = planet.GetMoon(l);
                        TreeItem<Object> moonItem = new TreeItem<>(moon);
                        planetItem.getChildren().add(moonItem);
                    }
                    
                    starItem.getChildren().add(planetItem);
                }
                
                galaxyItem.getChildren().add(starItem);
            }
            
            rootItem.getChildren().add(galaxyItem);
        }
        
        treeView.setRoot(rootItem);
        
        // Custom cell factory to display object names properly
        treeView.setCellFactory(tv -> new TreeCell<Object>() {
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(getDisplayName(item));
                }
            }
        });
    }
    
    /**
     * Gets appropriate display name for tree items
     */
    private String getDisplayName(Object item) {
        if (item instanceof cUniverse) {
            cUniverse u = (cUniverse) item;
            return "Universe (" + u.GetGalaxyCount() + " galaxies)";
        } else if (item instanceof cGalaxy) {
            cGalaxy g = (cGalaxy) item;
            return g.GetName() + " (" + g.GetStarCount() + " stars)";
        } else if (item instanceof cStar) {
            cStar s = (cStar) item;
            return s.GetName() + " (" + s.GetPlanetCount() + " planets)";
        } else if (item instanceof cPlanet) {
            cPlanet p = (cPlanet) item;
            if (p.GetMoonCount() > 0) {
                return p.GetName() + " (" + p.GetMoonCount() + " moons)";
            } else {
                return p.GetName();
            }
        }
        return item.toString();
    }
    
    /**
     * Displays detailed information about the selected object
     */
    private void DisplayObjectDetails(Object selectedObject) {
        if (selectedObject instanceof cUniverse) {
            DisplayUniverseDetails((cUniverse) selectedObject);
        } else if (selectedObject instanceof cGalaxy) {
            DisplayGalaxyDetails((cGalaxy) selectedObject);
        } else if (selectedObject instanceof cStar) {
            DisplayStarDetails((cStar) selectedObject);
        } else if (selectedObject instanceof cPlanet) {
            DisplayPlanetDetails((cPlanet) selectedObject);
        }
    }
    
    private void DisplayUniverseDetails(cUniverse universe) 
    {
        // Calculate aggregate statistics
        int totalStars = 0;
        int totalPlanets = 0;
        int totalMoons = 0;
        
        java.util.Map<String, Integer> galaxyTypeCount = new java.util.HashMap<>();
        java.util.Map<String, Integer> starTypeCount = new java.util.HashMap<>();
        java.util.Map<String, Integer> planetTypeCount = new java.util.HashMap<>();

        for (int i = 0; i < universe.GetGalaxyCount(); i++) 
        {
            cGalaxy galaxy = universe.GetGalaxy(i);
            String galaxyType = galaxy.GetType().toString().replace("k", "");
            galaxyTypeCount.put(galaxyType, galaxyTypeCount.getOrDefault(galaxyType, 0) + 1);

            for (int j = 0; j < galaxy.GetStarCount(); j++) 
            {
                cStar star = galaxy.GetStar(j);
                String starType = star.GetStarType().toString().replace("kClass", "");
                starTypeCount.put(starType, starTypeCount.getOrDefault(starType, 0) + 1);

                for (int k = 0; k < star.GetPlanetCount(); k++) 
                {
                    cPlanet planet = star.GetPlanet(k);
                    String planetType = planet.GetPlanetType().toString().replace("k", "");
                    planetTypeCount.put(planetType, planetTypeCount.getOrDefault(planetType, 0) + 1);
                    for(int e = 0; e < planet.GetMoonCount(); e++) {
                        cPlanet moon = planet.GetMoon(e);
                        String moonType = moon.GetPlanetType().toString().replace("k", "");
                        planetTypeCount.put(moonType, planetTypeCount.getOrDefault(moonType, 0) + 1);
                    }
                    totalMoons += planet.GetMoonCount();
                }
                totalPlanets += star.GetPlanetCount();
            }
            totalStars += galaxy.GetStarCount();
        }

        titleLabel.setText("Universe Details");

        StringBuilder props = new StringBuilder();
        props.append("═══════════════════════════════════════════════════════════════\n");
        props.append("                          UNIVERSE DETAILS                         \n");
        props.append("═══════════════════════════════════════════════════════════════\n\n");
        props.append("Total Galaxies: ").append(universe.GetGalaxyCount()).append("\n");
        props.append("Total Star Systems: ").append(totalStars).append("\n");
        props.append("Total Planets: ").append(totalPlanets).append("\n");
        props.append("Total Moons: ").append(totalMoons).append("\n");
        props.append("Total Celestial Objects: ").append(universe.GetGalaxyCount() + totalStars + totalPlanets + totalMoons).append("\n\n");
        
        if (universe.GetGalaxyCount() > 0) {
            props.append("BREAKDOWN BY GALACTIC CLASSIFICATION:\n");
            props.append("───────────────────────────────────────────────────────────────\n");
            for (int i = 0; i < universe.GetGalaxyCount(); i++) {
                cGalaxy galaxy = universe.GetGalaxy(i);
                props.append(String.format("%-15s | %-20s | %3d stars\n", 
                    galaxy.GetName(), 
                    galaxy.GetType().toString().replace("k", ""), 
                    galaxy.GetStarCount()));
            }
        }
        props.append("\n");
        if (!starTypeCount.isEmpty()) {
            props.append("BREAKDOWN BY STELLAR CLASSIFICATION:\n");
            props.append("───────────────────────────────────────────────────────────────\n");
            for (java.util.Map.Entry<String, Integer> entry : starTypeCount.entrySet()) {
                props.append(String.format("%-10s: %3d stars\n", entry.getKey() + "-type", entry.getValue()));
            }
        }
        props.append("\n");
        if (!planetTypeCount.isEmpty()) {
            props.append("BREAKDOWN BY PLANETARY CLASSIFICATION:\n");
            props.append("───────────────────────────────────────────────────────────────\n");
            for (java.util.Map.Entry<String, Integer> entry : planetTypeCount.entrySet()) {
                props.append(String.format("%-10s: %3d planets\n", entry.getKey() + "-type", entry.getValue()));
            }
        }

        propertiesArea.setText(props.toString());
    }
    
    private void DisplayGalaxyDetails(cGalaxy galaxy) 
    {
        // Calculate statistics
        int totalPlanets = 0;
        int totalMoons = 0;
        java.util.Map<String, Integer> starTypeCount = new java.util.HashMap<>();
        java.util.Map<String, Integer> planetTypeCount = new java.util.HashMap<>();

        for (int i = 0; i < galaxy.GetStarCount(); i++) {
            cStar star = galaxy.GetStar(i);
            String starType = star.GetStarType().toString().replace("kClass", "");
            starTypeCount.put(starType, starTypeCount.getOrDefault(starType, 0) + 1);
            for(int e = 0; e < star.GetPlanetCount(); e++) {
                cPlanet planet = star.GetPlanet(e);
                String planetType = planet.GetPlanetType().toString().replace("k", "");
                planetTypeCount.put(planetType, planetTypeCount.getOrDefault(planetType, 0) + 1);
                for(int j = 0; j < planet.GetMoonCount(); j++) {
                    cPlanet moon = planet.GetMoon(j);
                    String moonType = moon.GetPlanetType().toString().replace("k", "");
                    planetTypeCount.put(moonType, planetTypeCount.getOrDefault(moonType, 0) + 1);
                }
                totalMoons += planet.GetMoonCount();
            }
            totalPlanets += star.GetPlanetCount();
        }

        titleLabel.setText(galaxy.GetName() + " - Galaxy Details");

        StringBuilder props = new StringBuilder();
        props.append("═══════════════════════════════════════════════════════════════\n");
        props.append("                         GALAXY DETAILS                          \n");
        props.append("═══════════════════════════════════════════════════════════════\n\n");
        props.append("Galaxy Name: ").append(galaxy.GetName()).append("\n");
        props.append("Galactic Classification: ").append(galaxy.GetType().toString().replace("k", "")).append("\n");
        props.append("Total Star Systems: ").append(galaxy.GetStarCount()).append("\n");
        props.append("Total Planets: ").append(totalPlanets).append("\n");
        props.append("Total Moons: ").append(totalMoons).append("\n\n");
        
        if (!starTypeCount.isEmpty()) {
            props.append("BREAKDOWN BY STELLAR CLASSIFICATION:\n");
            props.append("───────────────────────────────────────────────────────────────\n");
            for (java.util.Map.Entry<String, Integer> entry : starTypeCount.entrySet()) {
                props.append(String.format("%-10s: %3d stars\n", entry.getKey() + "-type", entry.getValue()));
            }
        }
        props.append("\n");
        if (!planetTypeCount.isEmpty()) {
            props.append("BREAKDOWN BY PLANETARY CLASSIFICATION:\n");
            props.append("───────────────────────────────────────────────────────────────\n");
            for (java.util.Map.Entry<String, Integer> entry : planetTypeCount.entrySet()) {
                props.append(String.format("%-10s: %3d planets\n", entry.getKey() + "-type", entry.getValue()));
            }
        }
        
        propertiesArea.setText(props.toString());
    }
    
    private void DisplayStarDetails(cStar star) {
        titleLabel.setText(star.GetName() + " - Star System Details");
        
        // Calculate planetary statistics
        int totalMoons = 0;
        java.util.Map<String, Integer> planetTypeCount = new java.util.HashMap<>();
        
        for (int i = 0; i < star.GetPlanetCount(); i++) {
            cPlanet planet = star.GetPlanet(i);
            String planetType = planet.GetPlanetType().toString().replace("k", "");
            planetTypeCount.put(planetType, planetTypeCount.getOrDefault(planetType, 0) + 1);
            for(int e = 0; e < planet.GetMoonCount(); e++) {
                cPlanet moon = planet.GetMoon(e);
                String moonType = moon.GetPlanetType().toString().replace("k", "");
                planetTypeCount.put(moonType, planetTypeCount.getOrDefault(moonType, 0) + 1);
            }
            totalMoons += planet.GetMoonCount();
        }

        StringBuilder props = new StringBuilder();
        props.append("═══════════════════════════════════════════════════════════════\n");
        props.append("                      STAR SYSTEM DETAILS                       \n");
        props.append("═══════════════════════════════════════════════════════════════\n\n");
        props.append("Star Name: ").append(star.GetName()).append("\n");
        props.append("Stellar Classification: ").append(star.GetStarType().toString().replace("kClass", "")).append("-type\n");
        props.append("Total Planets: ").append(star.GetPlanetCount()).append("\n");
        props.append("Total Moons: ").append(totalMoons).append("\n\n");
        
        if (!planetTypeCount.isEmpty()) {
            props.append("BREAKDOWN BY PLANETARY CLASSIFICATION:\n");
            props.append("───────────────────────────────────────────────────────────────\n");
            for (java.util.Map.Entry<String, Integer> entry : planetTypeCount.entrySet()) {
                props.append(String.format("%-15s: %2d planets\n", entry.getKey(), entry.getValue()));
            }
        }
        
        propertiesArea.setText(props.toString());
    }
    
    private void DisplayPlanetDetails(cPlanet planet) 
    {
        titleLabel.setText(planet.GetName() + " - Planet Details");
        
        StringBuilder props = new StringBuilder();
        props.append("═══════════════════════════════════════════════════════════════\n");
        props.append("                        PLANET DETAILS                          \n");
        props.append("═══════════════════════════════════════════════════════════════\n\n");
        props.append("Planet Name: ").append(planet.GetName()).append("\n");
        props.append("Planetary Classification: ").append(planet.GetPlanetType().toString().replace("k", "")).append("\n");
        props.append("Total Moons: ").append(planet.GetMoonCount()).append("\n\n");
        
        if (planet.GetMoonCount() > 0) 
        {
            props.append("BREAKDOWN BY PLANETARY CLASSIFICATION:\n");
            props.append("───────────────────────────────────────────────────────────────\n");
            
            // List individual moons
            for (int i = 0; i < planet.GetMoonCount(); i++) 
            {
                cPlanet moon = planet.GetMoon(i);
                props.append(String.format("Moon %d: %s (%s)\n", 
                    i + 1, 
                    moon.GetName(), 
                    moon.GetPlanetType().toString().replace("k", "")));
            }

        } 
        else 
        {
            props.append("MOON SYSTEM:\n");
            props.append("───────────────────────────────────────────────────────────────\n");
            props.append("This planet has no natural satellites.\n");
        }
        
        propertiesArea.setText(props.toString());
    }
    
    /**
     * Refreshes the tree view
     */
    private void RefreshTreeView() 
    {
        BuildTreeStructure();
    }
    
    /**
     * Generates a new random universe
     */
    private void GenerateNewUniverse() {
        universe.ClearGalaxies();
        universe.RandomUniverse(1, 4, 12, 24, 1, 12, 0, 8);
        RefreshTreeView();
    }
    
    /**
     * Loads the test universe
     */
    private void LoadTestUniverse() {
        universe.ClearGalaxies();
        universe.TestUniverse();
        RefreshTreeView();
    }
}