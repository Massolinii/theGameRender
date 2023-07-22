import React, { useContext, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import {
  fetchCategory,
  fetchCollectionsFromCategory,
  fetchImagesFromCategory,
  fetchUserFavorites,
} from "../api";
import "../css/CategoryPage.css";
import ImageUploadModal from "./ImageUploadModal";
import { Alert, Button } from "react-bootstrap";
import { AuthContext } from "../AuthContext";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPlus, faHouseChimney } from "@fortawesome/free-solid-svg-icons";
import CollectionCreateModal from "./CollectionCreateModal";
import ImageCard from "./ImageCard";
import { UseImageActions } from "../hooks/UseImageActions";

const categoryToBgClass = {
  1: "env-category-bg",
  2: "obj-category-bg",
  3: "char-category-bg",
};

function CategoryPage() {
  const [isUploadModalOpen, setIsUploadModalOpen] = useState(false);
  const [isCollectionCreateModalOpen, setIsCollectionCreateModalOpen] =
    useState(false);
  const { id } = useParams();
  const [category, setCategory] = useState(null);
  const [collections, setCollections] = useState([]);
  const [images, setImages] = useState([]);
  const [selectedImages, setSelectedImages] = useState([]);
  const [uploadMessage, setUploadMessage] = useState(null);
  const { user } = useContext(AuthContext);

  const {
    favoriteImages,
    setFavoriteImages,
    copiedImageId,
    handleCopyClick,
    handleFavoriteToggle,
  } = UseImageActions(user, images);

  // Fetch category, collections and images on page load
  useEffect(() => {
    (async () => {
      try {
        const category = await fetchCategory(id);
        setCategory(category);
        const collections = await fetchCollectionsFromCategory(id);
        setCollections(collections);
        const images = await fetchImagesFromCategory(id);
        setImages(images);
      } catch (error) {
        console.error("Failed to fetch data:", error);
      }
    })();
  }, [id]);

  // Fetch user's favorite images on page load and when user updates
  useEffect(() => {
    (async () => {
      if (user && user.username) {
        try {
          const favoriteImages = await fetchUserFavorites(user.username);
          setFavoriteImages(favoriteImages);
        } catch (error) {
          console.error("Failed to fetch favorite images:", error);
        }
      }
    })();
  }, [user, setFavoriteImages]);

  const bgClass = categoryToBgClass[id];

  const openUploadModal = () => {
    setIsUploadModalOpen(true);
  };

  const closeUploadModal = () => {
    setIsUploadModalOpen(false);
  };

  const openCollectionCreateModal = () => {
    setIsCollectionCreateModalOpen(true);
  };

  const closeCollectionCreateModal = () => {
    setIsCollectionCreateModalOpen(false);
  };

  // Toggle image selection
  const handleImageClick = (id) => {
    if (selectedImages.includes(id)) {
      setSelectedImages(selectedImages.filter((imageId) => imageId !== id));
    } else {
      setSelectedImages([...selectedImages, id]);
    }
  };

  // Fetch images again after successful upload
  const handleUploadSuccess = async (message) => {
    setUploadMessage(message);

    try {
      const images = await fetchImagesFromCategory(id);
      setImages(images);
    } catch (error) {
      console.error("Failed to fetch images:", error);
    }
  };

  // Fetch collections again after successful creation
  const handleCollectionCreateSuccess = async (message) => {
    try {
      const collections = await fetchCollectionsFromCategory(id);
      setCollections(collections);
    } catch (error) {
      console.error("Failed to fetch collections:", error);
    }
  };

  if (!category) {
    return <div>Loading...</div>;
  }

  return (
    <div className="pt-5 full-screen">
      <div className={`category-page-bg ${bgClass}`}>
        <h1 className="category-page-name">{category.categoryName}</h1>
      </div>

      <div className="category-page-container text-white pt-2">
        <Link className="go-back-home p-3" to={"/"}>
          <FontAwesomeIcon icon={faHouseChimney} /> Return Home
        </Link>

        <h3 className="mt-4 px-3 py-1 to-color">
          {category.categoryName} themes
        </h3>
        <CollectionCreateModal
          isOpen={isCollectionCreateModalOpen}
          onClose={closeCollectionCreateModal}
          onCreateSuccess={handleCollectionCreateSuccess}
        />
        <p className="px-3 h3-subtitle">Select a theme </p>
        <ul className="list-unstyled ps-2">
          {collections.map((collection) => (
            <li key={collection.collectionID} className="p-2">
              <Link
                to={`/collection/${collection.collectionID}`}
                className="category-page-collection-list "
              >
                {collection.collectionName}
              </Link>
            </li>
          ))}
        </ul>
        {user && user.roles && user.roles.includes("ROLE_ADMIN") && (
          <Button
            className="add-collection mx-4 px-2 py-1"
            variant="outline-light"
            onClick={openCollectionCreateModal}
          >
            <FontAwesomeIcon icon={faPlus} /> Add Theme
          </Button>
        )}

        <h3 className="mt-4 px-3 py-1 to-color">
          All {category.categoryName} images{" "}
        </h3>
        <p className="px-3 h3-subtitle">Click on an image to see the prompt </p>

        {user && user.roles && user.roles.includes("ROLE_ADMIN") && (
          <Button
            className="add-image mx-4 mb-3 px-2 py-1"
            variant="outline-light"
            onClick={openUploadModal}
          >
            <FontAwesomeIcon icon={faPlus} /> Add Image
          </Button>
        )}
        {uploadMessage && <Alert variant="success">{uploadMessage}</Alert>}

        <ImageUploadModal
          isOpen={isUploadModalOpen}
          onClose={closeUploadModal}
          onUploadSuccess={handleUploadSuccess}
        />
        <div className="image-container">
          {images.map((image) => (
            <ImageCard
              image={image}
              key={image.imageID}
              handleImageClick={handleImageClick}
              handleCopyClick={handleCopyClick}
              copiedImageId={copiedImageId}
              selectedImages={selectedImages}
              isFavorite={favoriteImages.some(
                (img) => img.imageID === image.imageID
              )}
              handleFavoriteToggle={handleFavoriteToggle}
            />
          ))}
        </div>
        <Link className="go-back-home p-3" to={"/"}>
          <FontAwesomeIcon icon={faHouseChimney} /> Return Home
        </Link>
      </div>
    </div>
  );
}

export default CategoryPage;
