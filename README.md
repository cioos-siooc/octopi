# Octopi

When the application is deployed through Tomcat, complete [Swagger API](https://swagger.io/) 
documentation is available at http://\(HOST\):\(PORT\)/octopi/api/swagger.json

## Deployment

### Deploying the Dockerfile
1. In *octopi/src/main/resources/META-INF/* create a *context.xml* file based on the *context.template.xml* file. The databases uri and credentials need to be set there.
2. Build the image: `docker image build -t octopi-build:1.0 .`
3. Run the container: `docker container run -it -p 8080:8080 --name octopi-build octopi-build:1.0`

## Example Workflows
These workflow diagrams are best used in conjunction with the API documentation.

### Inserting a New Layer
![Workflow diagram: create a new layer](/diagrams/create_new_layer.png)
Note: the steps after "POST /layers" are in parallel because they can be executed in any order.

### Adding a Layer to a Category
![Workflow diagram: add a layer to a category](/diagrams/add_layer_to_category.png)

### Creating a New Category Hierarchy
![Workflow diagram: create a new category hierarchy](/diagrams/create_category_hierarchy.png)

### Inserting a new Topic Group Hierarchy
![Workflow diagram: create a new topic group hierarchy](/diagrams/create_topic_group_hierarchy.png)

