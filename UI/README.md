# StarViewer JavaFX Setup Instructions

## Prerequisites
You need to have JavaFX installed to run the StarViewer UI application.

## Option 1: Download JavaFX SDK (Recommended)

1. **Download JavaFX SDK:**
   - Go to https://gluonhq.com/products/javafx/
   - Download JavaFX SDK for your platform (Windows, Mac, Linux)
   - Extract it to a folder (e.g., `C:\Program Files\JavaFX\javafx-sdk-25`)

2. **VS Code Configuration:**
   - Open VS Code settings (Ctrl+,)
   - Search for "java.configuration.runtimes"
   - Add JavaFX to your runtime configuration:
   ```json
   "java.configuration.runtimes": [
       {
           "name": "JavaSE-11",
           "path": "C:\\Program Files\\Java\\jdk-11",
           "javadoc": "https://docs.oracle.com/en/java/javase/11/docs/api",
           "sources": "C:\\Program Files\\Java\\jdk-11\\lib\\src.zip"
       }
   ],
   "java.compile.nullAnalysis.mode": "automatic",
   "java.project.referencedLibraries": [
       "C:\\Program Files\\JavaFX\\javafx-sdk-25\\lib\\**\\*.jar"
   ]
   ```

3. **Launch Configuration:**
   Add VM arguments when running:
   ```
   --module-path "C:\Program Files\JavaFX\javafx-sdk-25\lib" --add-modules javafx.controls,javafx.fxml
   ```

## Option 2: Maven/Gradle (Alternative)

If you prefer using Maven, create a `pom.xml` with JavaFX dependencies:

```xml
<dependencies>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>25</version>
    </dependency>
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>25</version>
    </dependency>
</dependencies>
```

## Running the Application

Once JavaFX is configured, you can run the StarViewer application:

1. Compile: `javac --module-path path/to/javafx/lib --add-modules javafx.controls UI/StarViewerApp.java`
2. Run: `java --module-path path/to/javafx/lib --add-modules javafx.controls UI.StarViewerApp`

Or use VS Code's Run button if properly configured.

## Application Features

The StarViewer application provides:

- **Tree View (Left)**: Hierarchical view of Universe → Galaxies → Stars → Planets → Moons
- **Detail Panel (Right)**: Properties and children list for selected objects
- **Menu Options**: Generate new universes, load test data
- **Interactive**: Click any object in the tree to see its details

## Controls

- **Generate New Universe**: Creates a random universe with 1-4 galaxies
- **Load Test Universe**: Loads the predefined test universe
- **Refresh**: Rebuilds the tree view
- **Tree Navigation**: Click to expand/collapse, select for details