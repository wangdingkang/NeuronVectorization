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

In order to run c++ code, c++ must be supported by your compile (newest MINGW compiler should work fine on Windows OS). You will also need to install boost library.

## Notice

1). You may need to reset the paths of input folder and output folder in each main file.

2). ".des" files are outputs of the first step, trees with function values.
    ".pdg" files are persistence diagrams.
    ".pvec" files are vectors, also, there are two numbers on the first line representing the range of its persistence diagram.
    All files can be opened and read by txt editors.
