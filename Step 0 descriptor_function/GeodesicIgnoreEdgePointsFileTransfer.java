import java.util.List;


public class GeodesicIgnoreEdgePointsFileTransfer extends FileTransfer{
	// originally Euclidean
	public GeodesicIgnoreEdgePointsFileTransfer(String inputFile){
		this.inputFile = inputFile;
	}

	/**
	 * Compute the Giodesic distance ignore edge points between tree nodes
	 * @param root
	 * @param parentNode
	 * @param distance
	 */
	@Override
	protected void DFS(TreeNode root, TreeNode parentNode, double distance) {

		TreeNode temp = root;
		while(temp != null) {
			TreeNode tempParent = temp.getParentNode();
			if(tempParent != null) {
				double dist = temp.getVal().computeDistance(tempParent.getVal()) + tempParent.getDistance();
				temp.setDistance(dist);
			} else {
				temp.setDistance(0.0);
			}

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

//		System.out.println(root.getVal().x + " " + root.getVal().y + " " + root.getVal().z);
//		if(parentNode==null){ // root node
//			List<TreeNode> children = root.getChildren();
//			//***** + root.setDistance(0.0)
//			root.setDistance(0.0);
//			for(TreeNode child: children){
//				DFS(child, root, distance);
//			}
//		}else{
//			if(root.childrenNum()==0){ // leaf node
//				root.setParentNode(parentNode);
//				//***** + parentNode.getDistance()
//				double dist = root.getVal().computeDistance(parentNode.getVal()) + parentNode.getDistance();
//				root.setDistance(dist);
//			}else if(root.childrenNum()==1){ // point along edges, not tree node
//				List<TreeNode> children = root.getChildren();
//				for(TreeNode child: children){
//					DFS(child,parentNode, distance);
//				}
//			}else{ // tree node (with more than one child)
//				root.setParentNode(parentNode);
//				//***** + parentNode.getDistance()
//				double dist = root.getVal().computeDistance(parentNode.getVal()) + parentNode.getDistance();
//				root.setDistance(dist);
//				List<TreeNode> children = root.getChildren();
//				for(TreeNode child: children){
//					DFS(child, root, distance);
//				}
//			}
//		}




	}

}
