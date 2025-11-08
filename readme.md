# PL/BL/DL Application
- Designed and programmed **Presentation Layer, Business Layer,** and **Data Layer**
- **3-tier architecture** (PL/BL/DL) for "Student" and "Course" management with full CRUD operations
- Swing-based GUI with event-driven architecture (Action, Mouse, Item Listeners)
- PDFexport functionality using itext7; built with Gradle

---

# Running HR Project Layers
This project contains two different data layers. Any one may be used at a time.

## 1. PL-BL-DL Layers (File Handling)

Uses **File Handling** for the Data Layer and writes data into flat files.

**From `hr/pl/` directory:**  
Include the `.jar` files of the common folder, all 3 layers, and `pl\libs\itext\*`.

```bash
java -classpath ..\common\dist\hr-common.jar;
                ..\dl\dist\dl.jar;
                ..\bl\build\libs\bl.jar;
                .\build\libs\pl.jar;
                .\libs\itext7\*;. 
                com.thinking.machines.hr.pl.Main
```

## 2. PL-BL-DBDL Layers (MySQL Database)

Uses **MySQL Database** for the Data Layer and writes into database.

**From `hr/pl/` directory:**  
Include `.jar` file of common folder, .jar file of all 3 layers, `pl\libs\itext\*`, `dbdl\libs\mysql-connector-j-9.3.0.jar`.

```bash
java -classpath ..\common\dist\hr-common.jar;
                ..\dbdl\build\libs\dbdl.jar;
                ..\bl\build\libs\bl.jar;
                .\build\libs\pl.jar;
                .\libs\itext7\*;
                ..\dbdl\libs\*;. 
                com.thinking.machines.hr.pl.Main

```

---
### Implementation Status
Note: The presentation layer for the Student entity is not yet implemented
| Entity   | DL | DBDL | BL | PL |
|----------|----|----|----|----|
| Course   | Done | Done | Done | Done  |
| Student  | Done | Done | Done | **[PENDING]**  |