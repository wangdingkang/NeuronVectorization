# NeuronVectorization
Neuron vectorization tool using persistence diagrams. Modified from the original version (https://github.com/Nevermore520/NeuronTools).
More details can be found in the paper (https://journals.plos.org/plosone/article?id=10.1371/journal.pone.0182184).

## Test Samples

The input neurons and all intermediate & final results are in folder "Test cases & Results". The input neurons are 5 neurons selected from http://neuromorpho.org/. I used geodesic functions (option 1) in the first step.

## Work Flow

There are three steps to generate vector summaries of input neurons. To explain it in details, I will take samples under "Test cases & Results" folder as an example.

1). Run java code in "step 0 descriptor_function", it takes neuron trees in "0 input neurons" as input, and will output trees with descriptor function values assigned to each tree nodes. The output should be the same as the results in folder "1 tree with func value".

2). Run c++ code in "step 1 persistence_diagram", it takes the output of previous java code as its input, and its output should be persistence diagrams, and should be the same as the results in folder "2 persistence diags".

3). Run c++ code in "step 2 vectorization", similarly, it takes persistence diagrams as input, and its output will be vector summaries of neurons, and should be the same as the results in "3 vectors".

## Enviroment Setup

For both c++ and java code, I used Eclipse IDE, but Clion and Intellj should also work.

In order to run c++ code, c++11 compiler must be installed (newest MINGW compiler should work fine on Windows OS). You will also need to install boost library for compiling persistence diagram code.

After compilation, you can run the code step by step using following commands (replace all $variables with your actual value):

### Calculate descriptor function values
"java FTMain $input_folder_path $output_folder_path $descriptor_function"

We have 6 different descriptor functions available, 1 is the default one.

0: Geodesic distance along neuron tree edges from tree nodes to root

1: Euclidean distance along neuron tree edges from tree nodes to root

2: Weighted Geodesic distance along neuron tree edges from tree nodes to root

3: Euclidean distance from tree nodes to root

4: Density count at each tree node from root to farthest tree node to root

5: Use y-coordinate as descriptor function

### Generate persistence diagram
"main $input_folder_path $output_folder_path"

### Vectorize persistence diagram
"vectorization $input_folder_path $output_folder_path"

## Notice

".des" files are outputs of the first step, trees with function values.

".pdg" files are persistence diagrams.

".pvec" files are vectors, also, there are two numbers on the first line representing the range of its persistence diagram.

All files can be opened and read by txt editors.
