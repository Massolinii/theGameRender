import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import {
  fetchCategory,
  fetchCollectionsFromCategory,
  fetchImagesFromCategory,
} from "../api";
import "../css/CategoryPage.css";
import ImageUploadModal from "./ImageUploadModal";

const categoryToBgClass = {
  1: "env-category-bg",
  2: "obj-category-bg",
  3: "char-category-bg",
};

function CategoryPage() {
  const [isUploadModalOpen, setIsUploadModalOpen] = useState(false);
  const { id } = useParams();
  const [category, setCategory] = useState(null);
  const [collections, setCollections] = useState([]);
  const [images, setImages] = useState([]);
  const [selectedImages, setSelectedImages] = useState([]);

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

  const bgClass = categoryToBgClass[id];

  const openUploadModal = () => {
    setIsUploadModalOpen(true);
  };

  const closeUploadModal = () => {
    setIsUploadModalOpen(false);
  };

  const handleImageClick = (id) => {
    if (selectedImages.includes(id)) {
      setSelectedImages(selectedImages.filter((imageId) => imageId !== id));
    } else {
      setSelectedImages([...selectedImages, id]);
    }
  };

  if (!category) {
    return <div>Loading...</div>;
  }

  return (
    <div className="pt-5">
      <div className={`category-page-bg ${bgClass}`}>
        <h1 className="category-page-name">{category.categoryName}</h1>
      </div>

      <div className="ps-5 go-back-home-container">
        <Link className="go-back-home" to={"/"}>
          Return Home
        </Link>
        <button onClick={openUploadModal}>Upload Image</button>
        <ImageUploadModal
          isOpen={isUploadModalOpen}
          onClose={closeUploadModal}
        />

        <h2 className="pt-3">Collections:</h2>
        <ul>
          {collections.map((collection) => (
            <li key={collection.collectionID}>{collection.collectionName}</li>
          ))}
        </ul>

        <h2>Images:</h2>
        <div className="image-container">
          {images.map((image) => (
            <div key={image.imageID}>
              <img
                src={image.url}
                alt=""
                className="image-in-category"
                onClick={() => handleImageClick(image.imageID)}
              />
              {selectedImages.includes(image.imageID) && (
                <p className="prompt-text">{image.promptText}</p>
              )}
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}

export default CategoryPage;
