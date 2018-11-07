import java.util.ArrayList;
public class BinaryTree{

	public Node root;

	public BinaryTree(int root){
		this.root = new Node(root);
	}

	public BinaryTree(Node root){
		this.root = root;
	}

	public void insert(int i){
		boolean next = true;
		Node a = new Node(i);
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

	public void delete(int i){
		boolean next = true;
		Node b = root;
		while(next){
			if(b.val==i){
				if(b.parent.left.val==i){
					b.parent.left = null;
				}else{
					b.parent.right = null;
				}
				cycleRotations(b.parent);
				System.out.println(" - REMOVAL SUCCESSFUL - ");
				next = false;
			}
			if(i>=b.val){
				b = b.right;
			}else if(i<b.val){
				b = b.left;
			}
			if(b==null){
				next = false;
				System.out.println(" - ELEMENT NOT FOUND - ");
			}
		}
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

	


	public int atLevel(Node a){
		boolean next = true;
		Node b = root;
		int level = 0;
		while(next&&b!=null){
			if(b.equals(a)){
				return level;
			}
			if(a.val>=b.val){
				b = b.right;
			}else if(a.val<b.val){
				b = b.left;
			}
			level++;
		}
		return -1;
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
			n = a.right;
			n.parent = null;
			root = n;
			root.left.parent = root;
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
			n.parent.left = n.left;
			n.left = n.left.right;
			n.parent.left.right = n;
			n = a.left;
			n.parent = null;
			root = n;
			root.right.parent = root;
		}
	}

	public void verbose(Node n){
		if(n.right==null&&n.left==null){
			System.out.println(n);
			return;
		}else if(n.right==null){
			System.out.println(n);
			verbose(n.left);
		}else if(n.left==null){
			verbose(n.right);
			System.out.println(n);

		}else{
			verbose(n.right);
			System.out.println(n);
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
	public int getHeight(){
		return Math.max(rHeight(root),lHeight(root));
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