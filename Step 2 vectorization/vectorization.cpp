//============================================================================
// Name        : vector_new.cpp
// Author      : Dingkang Wang
// Version     : 1.0.0
// Copyright   : Your copyright notice
// Description : Hello World in C++, Ansi-style
//============================================================================

#include <string.h>
#include <io.h>
#include <stdlib.h>
#include <iomanip>
#include <cmath>
#include <fstream>
#include <iostream>
#include <sstream>
#include <vector>
#include <cassert>
using namespace std;

/*
 *get all file names with path from {path}. and save them to {files}. exd is the file extension (.txt, .swc)
 */
void getFiles( string path, string exd, vector<string>& files )
{
	long hFile = 0;
	struct _finddata_t fileinfo;
	string pathName, exdName;

	if (0 != strcmp(exd.c_str(), ""))
	{
		exdName = "\\*." + exd;
	}
	else
	{
		exdName = "\\*";
	}

	if((hFile = _findfirst(pathName.assign(path).append(exdName).c_str(),&fileinfo)) !=  -1)
	{
		do
		{
			if((fileinfo.attrib &  _A_SUBDIR))
			{
				if(strcmp(fileinfo.name,".") != 0  &&  strcmp(fileinfo.name,"..") != 0)
					getFiles(pathName.assign(path).append("\\").append(fileinfo.name), exd, files );
			}
			else
			{
				if(strcmp(fileinfo.name,".") != 0  &&  strcmp(fileinfo.name,"..") != 0)
					files.push_back(pathName.assign(path).append("\\").append(fileinfo.name));
			}
		}while(_findnext(hFile, &fileinfo)  == 0);
		_findclose(hFile);
	}
}

vector<string> readFiles(string filePath){
	vector<string> files;
	getFiles(filePath, "pdg", files);
	return files;
}

int getIndex(string fileName){
	int slashIndex = fileName.find_last_of('\\');
	string name = fileName.substr(slashIndex+1);
	int underscoreIndex = name.find_first_of('_');
	int index = atoi(name.substr(0,underscoreIndex).c_str());
	return index;
}

vector<string> getFixIndexFiles(vector<string> files){
	vector<string> filesFixIndex;
	filesFixIndex.resize(files.size());
	for(unsigned int i=0;i<files.size();i++){
		string next = files[i];
		int index = getIndex(next);
		filesFixIndex[index-1] = next;
	}
	return filesFixIndex;
}

vector<vector<double>> readDiagram(string file){
	vector<vector<double>> diagram;
	std::ifstream ifs;
	ifs.open(file.c_str());
	string line;
	double x, y;
	vector<double> X; // x coordinate
	vector<double> Y; // y coordinate
	while(getline(ifs, line)){
		istringstream iss(line);
		if(iss >> x >> y){
			X.push_back(abs(x));
			Y.push_back(abs(y));
		}
	}
	diagram.push_back(X);
	diagram.push_back(Y);
	ifs.close();
	ifs.clear();
	return diagram;
}

double get_max_range(vector<vector<double> > diagram) {
	double max_range = -1e8;
	for(auto x : diagram[0]) {
		max_range = max(max_range, x);
	}
	return max_range;
}

double get_min_range(vector<vector<double> > diagram) {
	double min_range = 1e8;
	for(auto x : diagram[0]) {
		min_range = min(min_range, x);
	}
	return min_range;
}

vector<double> diagram_to_vec(vector<vector<double>> diagram, int vec_len, double sigma, double min_range, double max_range ) {
		vector<double> vec;
		double upBound = max_range;
		double lowBound = min_range;
		double interval = (upBound - lowBound) / (vec_len-1);
		sigma = 2 * sigma * sigma;
		int j = 0;
		for(double pos = lowBound; j < vec_len; pos+=interval, ++j){
			double value = 0;
			for(size_t i = 0; i < diagram[0].size(); i++){
				double alpha = fabs(diagram[0][i] - diagram[1][i]);
				double dist = diagram[0][i] - pos;
				value += alpha * exp(-(dist * dist) / sigma);
			}
			vec.push_back(value);
		}
		return vec;
}

void print_vec(vector<double> vec, double min_range, double max_range, string filepath) {
	ofstream fout;
	fout.open(filepath);
	fout << min_range << " " << max_range << endl;
	for (auto v : vec) {
		fout << v << " ";
	}
	fout.close();
}

string get_filename(const string& s) {
   char sep = '/';
#ifdef _WIN32
   sep = '\\';
#endif
   string res = "";
   size_t i = s.rfind(sep, s.length());
   if (i == string::npos) {
	   res = s;
   } else {
	   res = s.substr(i + 1);
   }
   size_t j = res.rfind('.', res.length());
   if (j == string::npos) {
	   return res;
   } else {
	   return res.substr(0, j);
   }
}

void calculate(string diagrams_folder, string vector_folder, int vec_len, double sigma){

	vector<string> files = getFixIndexFiles(readFiles(diagrams_folder));

	for(auto file : files) {
		vector<vector<double> > diagram = readDiagram(file);
		double min_range = get_min_range(diagram);
		double max_range = get_max_range(diagram);
		cout << "Processing " << file << ": min range = " << min_range << ", max range = " << max_range << endl;
		vector<double> vector = diagram_to_vec(diagram, vec_len, sigma, min_range, max_range);
		string output_path = vector_folder + '\\' + get_filename(file) + ".pvec";
		print_vec(vector, min_range, max_range, output_path);
	}

}


int main(int argc, char **argv){
	if(argc != 3){
		cout << "usage: PDmain <input folder name> <output folder name>" << endl;
		return 0;
	}
	string diagrams_folder(argv[1]);
	string vector_folder(argv[2]);
	
	double sigma = 50;
	int vec_len = 100;
	calculate(diagrams_folder, vector_folder, vec_len, sigma);
}

