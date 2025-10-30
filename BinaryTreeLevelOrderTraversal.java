// 102. Binary Tree Level Order Traversal
// https://leetcode.com/problems/binary-tree-level-order-traversal/description/


// Solution 1 => DFS

/**
 * Time Complexity: O(n) since we process every node
 * Space Complexity: O(h) where h is depth of the tree
 */

class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if(root == null){
            return result;
        }

        helper(root, 0, result);

        return result;
    }

    private void helper(TreeNode root, int level, List<List<Integer>> result){
        // base condition
        if(root == null){
            return;
        }

        // logic
        if(result.size() == level){ // if result size is equal to level, we havent stored elements for that level
            result.add(new ArrayList<>());
        }
        result.get(level).add(root.val);

        helper(root.left, level + 1, result);
        helper(root.right, level + 1, result);
    }
}


// Solution 2 => BFS

/**
 * Time Complexity: O(n) since we process every node
 * Space Complexity: O(n / 2) = O(n) since at last level max width would be n/2
 */

class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        List<List<Integer>> result = new ArrayList<>();

        if(root == null){
            return result;
        }

        q.add(root); // add first element to queue

        while(!q.isEmpty()){
            int size = q.size(); // track size of elements at each level
            List<Integer> levelList = new ArrayList<>();

            for(int i = 0 ; i < size ; i++){ // remove elements from q from left to right for that level
                TreeNode curr = q.poll();
                
                levelList.add(curr.val); // add to level

                if(curr.left != null){
                    q.add(curr.left);
                }

                if(curr.right != null){
                    q.add(curr.right);
                }
            }

            result.add(levelList);
        }

        return result;
    }
}