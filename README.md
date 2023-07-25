# The Game Render

The Game Render is a showcase website that presents a collection of AI-generated images using Midjourney. It provides developers with a wide range of prompts and assets for game development, including drafts, characters, environments, props, and various other resources

## Features

- Explore a vast collection of AI-generated images for game development.
- Find inspiration and resources for different aspects of game development, including characters, environments, props, and more.
- Save your favorite items to easily access them later in the "Favorites" section.

## Installation

### 1. Clone the repository:
  ```
  git clone https://github.com/Massolinii/theGameRender
  ```
### 2. Set up the frontend:
- Navigate to the frontend directory:
  ```
  cd theGameRender/game_render_frontend
  ```
- Install dependencies:
  ```
  npm install
  ```

### 3. Set up the backend:
- Navigate to the backend directory:
  ```
  cd theGameRender/game_render_backend
  ```
- Configure the PostgreSQL database connection in `application.properties`.
  <br />
- Restore the database from backup (OPTIONAL, strongly recommended):

  Download the backup file from this link :
    ```
    https://www.dropbox.com/scl/fi/hvxcq9sn0cnafr4dawnl4/game_render_backup_25-7-23?rlkey=cfqkcooujl9rprpj472jq9p1m&dl=0
    ```
  Restore your PostgreSQL database using the downloaded backup file. You can do this using pgAdmin:

  Open pgAdmin and connect to your database.
  Right-click on your database name in the browser panel and select "Restore..."
  In the "Filename" field, click on the "..." button and select the downloaded backup file.
  Click "Restore" to start the restore process.

- Build and run the backend server.

### 4. Start the frontend:
- Inside the frontend directory, start the development server:
  ```
  npm start
  ```

### 5. Access the application at `http://localhost:3000` in your web browser.

## Technologies Used

- Frontend:
  - JavaScript
  - React
  - Bootstrap

- Backend:
  - Java
  - Spring Boot
  - PostgreSQL

## Contributing

Contributions are welcome! If you'd like to contribute to The Game Render, please follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bug fix.
3. Make your changes and commit them.
4. Push your changes to your fork.
5. Submit a pull request explaining your changes.

## License

The Game Render is released under the [MIT License](https://opensource.org/licenses/MIT).

## Acknowledgments

We would like to express our gratitude to the developers and contributors of the following technologies and libraries used in this project, but especially to Midjourney's developers for their amazing works which insipired this project.


## Contact

For any inquiries or feedback, please contact me at massimilianoesposito548@gmail.com or on [Linkedin](https://www.linkedin.com/in/massolini/).

Happy gaming and creating!
