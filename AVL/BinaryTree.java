import java.util.ArrayList;
public class BinaryTree{

	public Node root;

	public BinaryTree(int root){
		this.root = new Node(root);
	}

	public BinaryTree(Node root){
		this.root = root;
	}

	public void insert(Node a){
		boolean next = true;
		Node b = root;
		while(next){
			if(a.val>=b.val){
				if(b.right==null){
					b.right = a;
					a.parent = b;
					next = false;
				}
				else{
					b = b.right;
				}
			}else if(a.val<b.val){
				if(b.left==null){
					b.left = a;
					a.parent = b;
					next = false;
				}
				else{
					b = b.left;
				}
			}
		}
		cycleRotations(a);
	}

	public void cycleRotations(Node a){
		Node c = a;
		while(!c.equals(root)){
			applyRotation(c);
			c = c.parent;
		}
		applyRotation(c);
	}

	public void applyRotation(Node c){
		if(Math.abs(lHeight(c)-rHeight(c))>1){ // rotation toogle
				if(rHeight(c)>lHeight(c)){ // R
					if(rHeight(c.right)>lHeight(c.right)){ // RR
						lRotate(c);
					}else{ // RL
						rRotate(c.right);
						lRotate(c);
					}
				}else{ // L
					if(lHeight(c.left)>rHeight(c.left)){ // LL
						rRotate(c);
					}else{
						lRotate(c.left);
						rRotate(c);
					}
				}
			}
	}

	public void delete(Node a){
		boolean next = true;
		Node b = root;
		while(next){
			if(a.val>=b.val){
				if(b.equals(a)){
					b.parent.right = null;
					cycleRotations(b.parent);
					next = false;
				}
				else{
					b = b.right;
				}
			}else if(a.val<b.val){
				if(b.equals(a)){
					b.parent.left = null;
					cycleRotations(b.parent);
					next = false;
				}
				else{
					b = b.left;
				}
			}
		}
	}

	public void lRotate(Node n){
		//figure out if root is left or right of parent
		if(n.parent!=null){
			if(n.parent.right!=null&&n.parent.right.equals(n)){
				n.parent.right = n.right;
				n.right = n.right.left;
				n.parent.right.left = n;
			}else if(n.parent.left!=null&&n.parent.left.equals(n)){
				n.parent.left = n.right;
				n.right = n.right.left;
				n.parent.left.left = n;
			}
		}else{ // n = root
			Node a = new Node(-1); // turn node
			a.right = n;
			n.parent = a;
			n.parent.right = n.right;
			n.right = n.right.left;
			n.parent.right.left = n;
			root = a.right;
		}
	}

	public void rRotate(Node n){
		//figure out if root is left or right of parent
		if(n.parent!=null){
			if(n.parent.right!=null&&n.parent.right.equals(n)){
				n.parent.right = n.left;
				n.left = n.left.right;
				n.parent.right.right = n;
			}else if(n.parent.left!=null&&n.parent.left.equals(n)){
				n.parent.left = n.left;
				n.left = n.left.right;
				n.parent.left.right = n;
			}
		}else{ // n = root
			Node a = new Node(-1); // turn node
			a.right = n;
			n.parent = a;
			n.parent.right = n.left;
			n.left = n.left.right;
			n.parent.right.right = n;
			root = a.right;
		}
	}

	public void verbose(Node n){
		System.out.println(n);
		if(n.left==null&&n.right==null){
		    return;
		}else if(n.left==null){
			verbose(n.right);
		}else if(n.right==null){
			verbose(n.left);
		}else{
			verbose(n.right);
			verbose(n.left);
		}
	}


	public int rHeight(Node n){
		if(n.right==null){
			return 1;
		}
		return Math.max(rHeight(n.right),lHeight(n.right)) + 1;
	}

	public int lHeight(Node n){
		if(n.left==null){
			return 1;
		}
		return Math.max(rHeight(n.left),lHeight(n.left)) + 1;
	}

	public static void main(String[] args){
		BinaryTree bt = new BinaryTree(10);
		bt.insert(new Node(12));
		Node n = new Node(13);
		bt.insert(n);
	

		bt.verbose(bt.root);
		System.out.println("test");
		bt.delete(n);
		bt.verbose(bt.root);
	}
}
class Node{
	public Node left, right, parent;
	public int val;
	public Node(int val){
		this.val = val;
	}
	public String toString(){
		return ""+val;
	}
}