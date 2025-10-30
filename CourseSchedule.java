// 207. Course Schedule
// https://leetcode.com/problems/course-schedule/description/


/**
 * Time Complexity: O(V + E)
 * Space Complexity:  O(V + E)
 */

// Solution 1 => BFS

class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> map = new HashMap<>(); // map to maintain relations for each course
        int indegree[] = new int[numCourses]; // track no. of courses required to take particular course

        for(int i = 0 ; i < prerequisites.length ; i++){
            indegree[prerequisites[i][0]]++; // maintain how many courses are required to take this course (index represents course)

            if(!map.containsKey(prerequisites[i][1])){
                map.put(prerequisites[i][1], new ArrayList<>());
            }

            map.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }

        Queue<Integer> queue = new ArrayDeque<>();

        int count = 0;

        for(int i = 0 ; i < indegree.length ; i++){
            if(indegree[i] == 0){ // check if this course is independent and does not require any pre-req
                queue.add(i);
                count++;
            }
        }

        if(queue.isEmpty()){ // if queue is empty, we have inter-dependency
            return false;
        }

        while(!queue.isEmpty()){
            int course = queue.poll(); 

            List<Integer> depCourses =  map.get(course);

            if(depCourses == null){
                continue;
            }

            for(int i = 0 ; i < depCourses.size() ; i++){
                if(indegree[depCourses.get(i)] > 0){ 
                    indegree[depCourses.get(i)]--; // reduce dependecy of course by 1

                    if(indegree[depCourses.get(i)] == 0){ // if zero, we can take this course
                        queue.add(depCourses.get(i));
                        count++;
                    }
                }
            }
        }

        if(count == numCourses){ // we can complete all courses
            return true;
        }

        return false;
    }
}

// Solution 2 => DFS

/**
 * Time Complexity: O(V + E)
 * Space Complexity:  O(V + E)
 */

class Solution {

    private boolean result;

    private Map<Integer, List<Integer>> map;

    private int visited[];
    private int path[];

    public boolean canFinish(int numCourses, int[][] prerequisites) { 

        result = true;
        visited = new int[numCourses];
        path = new int[numCourses];

        map = new HashMap<>();

        for(int i = 0 ; i < prerequisites.length ; i++){

            if(!map.containsKey(prerequisites[i][1])){
                map.put(prerequisites[i][1], new ArrayList<>());
            }

            map.get(prerequisites[i][1]).add(prerequisites[i][0]);
        }

        for(int i = 0 ; i < numCourses ; i++){
            dfs(i);
        }

        return result;
    }

    private void dfs(int i){

        if(path[i] == 1){ // if this course was in path, there is inter-dependecy
            result = false;
        }

        if(visited[i] == 1){ // if we have this course in visited, dont process again since we have already checked for cycle
            return;
        }

        visited[i] = 1;
        path[i] = 1;

        List<Integer> neighbours = map.get(i);

        if(neighbours == null){
            path[i] = 0;
            return;
        }

        for(int j = 0 ; j < neighbours.size() ; j++){
            dfs(neighbours.get(j));
        }

        path[i] = 0;  // remove course from path since we are done with this path
    }
}