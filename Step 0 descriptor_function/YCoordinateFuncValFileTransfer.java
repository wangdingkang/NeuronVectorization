
public class YCoordinateFuncValFileTransfer extends FileTransfer{
	private TreeNode currentRoot;
	public YCoordinateFuncValFileTransfer(String inputFile){
		this.inputFile = inputFile;
		currentRoot = null;
	}
	@Override
	protected void DFS(TreeNode root, TreeNode parentNode, double distance) {
		TreeNode temp = root;
		while(temp != null) {
			TreeNode tempParent = temp.getParentNode();
			temp.setDistance(temp.getVal().y);

			TreeNode head = temp;
			while(temp != null) {
				TreeNode child = temp.nextChild();
				if(child == null)  {
					temp = temp.getParentNode();
					head = temp;
				}
				else if(child.getChildren().size() != 1){
					child.setParentNode(head);
					temp = child;
					break;
				} else {
					temp = child;
				}
			}
		}


		// TODO Auto-generated method stub
//		if(parentNode==null){
//			root.setDistance(root.getVal().y);
//			this.currentRoot = root;
//			for(TreeNode child: root.getChildren()){
//				DFS(child, root, distance);
//			}
//		}else{
//			if(root.childrenNum()==0){ // leaf node
//				root.setParentNode(parentNode);
//				root.setDistance(root.getVal().y);
//			}else if(root.childrenNum()==1){ // edge point
//				for(TreeNode child: root.getChildren()){
//					DFS(child,parentNode, distance);
//				}
//			}else{ // tree node
//				root.setParentNode(parentNode);
//				root.setDistance(root.getVal().y);
//				for(TreeNode child: root.getChildren()){
//					DFS(child, root, distance);
//				}
//			}
//		}
	}

}
