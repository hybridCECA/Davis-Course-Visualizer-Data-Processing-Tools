# Davis-Course-Visualizer-Data-Processing-Tools

Programs to process data for the [Davis-Course-Visualizer](https://github.com/Wanganator414/Davis-Course-Visualizer)

* course-processing: Bash scripts for downloading and parsing courses from the general catalog
* major-requirements-processing: Bash scripts for downloading and parsing major requirement courses 
* CPA.java: Implements the CPA algorithm to parse the prerequisites for courses
* FlowChartGenerator: Uses courses, major requirements, and parsed prerequisites to generate perfect flowcharts

**Chen's Prerequisite Algorithm**

Before
* Remove “xx or better”
* Add square brackets to all classes

General
* Operate on objects inside ()
* Replace (objects) with {element}

Operation
* Right to left, if “and” or “&” is between two elements, merge them into {e1&e2}
* Right to left, if “or” is between two elements, merge, them into {e1|e2}
* “suggested” and “recommended” delete the element to the left
* Right to left, if there is nothing between two elements, merge them into {e1&e2}
