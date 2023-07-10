import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import {
  fetchCategory,
  fetchCollectionsFromCategory,
  fetchImagesFromCategory,
} from "../api";

function Category() {
  const { id } = useParams();
  const [category, setCategory] = useState(null);
  const [collections, setCollections] = useState([]);
  const [images, setImages] = useState([]);

  useEffect(() => {
    (async () => {
      try {
        const category = await fetchCategory(id);
        setCategory(category);
      } catch (error) {
        console.error("Failed to fetch category:", error);
      }

      try {
        const collections = await fetchCollectionsFromCategory(id);
        setCollections(collections);
      } catch (error) {
        console.error("Failed to fetch collections:", error);
      }

      try {
        const images = await fetchImagesFromCategory(id);
        setImages(images);
      } catch (error) {
        console.error("Failed to fetch images:", error);
      }
    })();
  }, [id]);

  if (!category) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>{category.categoryName}</h1>

      <h2>Collections:</h2>
      <ul>
        {collections.map((collection) => (
          <li key={collection.collectionID}>{collection.collectionName}</li>
        ))}
      </ul>

      <h2>Images:</h2>
      <div style={{ display: "flex", flexWrap: "wrap" }}>
        {images.map((image) => (
          <div key={image.imageID} style={{ margin: "1rem" }}>
            <img
              src={image.url}
              alt=""
              style={{ width: "400px", height: "400px" }}
            />
          </div>
        ))}
      </div>
      <a href="/home">Go Back</a>
    </div>
  );
}

export default Category;
