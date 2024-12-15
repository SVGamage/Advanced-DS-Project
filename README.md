# Graph Algorithm Program

This program allows you to run graph algorithms in two modes: **Random Mode** and **File Mode**. It supports both Leftist Heaps and Fibonacci Heaps for graph processing. Below are detailed instructions for usage and input file formats.

## Usage

### Random Mode
Run the program in Random Mode to generate a random graph with specified parameters:

```bash
java Main -r n d x
```
ex: ```java Main -r 1000 10 0```
#### Arguments:
- **n**: Number of vertices in the graph.
- **d**: Density percentage of the graph (0 to 100).
- **x**: Source vertex.

### File Mode
Run the program in File Mode to process a graph from an input file. Depending on the data structure used, select either the Leftist Heap or the Fibonacci Heap:

#### Leftist Heap
```bash
java Main -l filename
```
ex: ```java Main -l input.txt```
#### Fibonacci Heap
```bash
java Main -f filename
```
ex: ```java Main -f input.txt```
### Input File Format
When using File Mode, the input file must follow this format:

1. **First line**: Source vertex.
2. **Second line**: Two integers `n` and `m`, representing the number of vertices and edges, respectively.
3. **Following `m` lines**: Each line represents an edge in the format:
   ```
   v1 v2 c
   ```
   Where:
    - **v1**: The starting vertex of the edge.
    - **v2**: The ending vertex of the edge.
    - **c**: The cost associated with the edge.

### Example Input File
```
1
5 6
1 2 3
1 3 2
2 4 6
3 4 4
3 5 2
4 5 1
```

### Output
The program computes the graph algorithm's result based on the provided input and selected mode, outputting details about paths, costs, or other relevant metrics depending on the implementation.

## Requirements
- **Java Runtime Environment (JRE)** to execute the program.

## Compilation
If the program is provided in source code format, you can compile it using the following command:

```bash
javac *.java
```

## Execution
Once compiled, run the program as described above using the appropriate command for the desired mode.

## License
This project is open-source and available for educational and personal use. Feel free to modify and adapt it to your needs.
