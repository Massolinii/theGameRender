import React, { useContext, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { fetchCollection, fetchImagesFromCollection } from "../api";
import "../css/CategoryPage.css";
import ImageUploadModal from "./ImageUploadModal";
import { Alert, Button } from "react-bootstrap";
import { AuthContext } from "../AuthContext";
import copy from "clipboard-copy";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCopy,
  faPlus,
  faCheck,
  faCircleLeft,
  faHouseChimney,
} from "@fortawesome/free-solid-svg-icons";
import ImageCard from "./ImageCard";

const categoryToBgClass = {
  1: "env-category-bg",
  2: "obj-category-bg",
  3: "char-category-bg",
};

const CollectionPage = () => {
  const [isUploadModalOpen, setIsUploadModalOpen] = useState(false);
  const { id } = useParams();
  const [collection, setCollection] = useState(null);
  const [images, setImages] = useState([]);
  const [selectedImages, setSelectedImages] = useState([]);
  const [uploadMessage, setUploadMessage] = useState(null);
  const { user } = useContext(AuthContext);
  const [copiedImageId, setCopiedImageId] = useState(null);

  useEffect(() => {
    (async () => {
      try {
        const collection = await fetchCollection(id);
        setCollection(collection);
      } catch (error) {
        console.error("Failed to fetch collection:", error);
      }

      try {
        const images = await fetchImagesFromCollection(id);
        setImages(images);
      } catch (error) {
        console.error("Failed to fetch images:", error);
      }
    })();
  }, [id]);

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

  const handleCopyClick = (text, id) => {
    copy(text);
    setCopiedImageId(id);
    setTimeout(() => setCopiedImageId(null), 3200);
  };

  if (!collection) {
    return <div>Loading...</div>;
  }

  const bgClass = categoryToBgClass[collection.category.categoryID];

  const handleUploadSuccess = async (message) => {
    setUploadMessage(message);

    try {
      const images = await fetchImagesFromCollection(id);
      setImages(images);
    } catch (error) {
      console.error("Failed to fetch images:", error);
    }
  };

  return (
    <tt>
      <div className="category-page-container pt-5 full-screen">
        <div className={`category-page-bg ${bgClass}`}>
          <h1 className="category-page-name">
            {collection.category.categoryName} - {collection.collectionName}
          </h1>
        </div>

        <div className="text-white pt-2">
          <Link
            className="go-back-home p-3"
            to={`/category/${collection.category.categoryID}`}
          >
            <FontAwesomeIcon icon={faCircleLeft} /> Return to{" "}
            {collection.category.categoryName}
          </Link>
          <br />
          <Link className="go-back-home p-3" to={"/"}>
            <FontAwesomeIcon icon={faHouseChimney} /> Return Home
          </Link>

          <h3 className="mt-5 px-3 py-1 to-color">
            All {collection.category.categoryName} - {collection.collectionName}{" "}
            images{" "}
            {user && user.roles && user.roles.includes("ROLE_ADMIN") && (
              <Button
                className="add-image"
                variant="outline-light"
                onClick={openUploadModal}
              >
                <FontAwesomeIcon icon={faPlus} />
              </Button>
            )}
          </h3>
          <p className="px-3 h3-subtitle">
            Click on an image to see the prompt{" "}
          </p>

          {uploadMessage && <Alert variant="success">{uploadMessage}</Alert>}

          <ImageUploadModal
            isOpen={isUploadModalOpen}
            onClose={closeUploadModal}
            onUploadSuccess={handleUploadSuccess}
          />
          <div className="image-container">
            {images.length > 0 ? (
              images.map((image) => (
                <ImageCard
                  image={image}
                  key={image.imageID}
                  handleImageClick={handleImageClick}
                  handleCopyClick={handleCopyClick}
                  copiedImageId={copiedImageId}
                  selectedImages={selectedImages}
                />
              ))
            ) : (
              <div className="empty-collection-message p-5">
                <h2>Ops! Looks like this collection is empty.</h2>
              </div>
            )}
          </div>
          <Link
            className="go-back-home p-3"
            to={`/category/${collection.category.categoryID}`}
          >
            <FontAwesomeIcon icon={faCircleLeft} /> Return to{" "}
            {collection.category.categoryName}
          </Link>
          <br />
          <Link className="go-back-home p-3" to={"/"}>
            <FontAwesomeIcon icon={faHouseChimney} /> Return Home
          </Link>
        </div>
      </div>
    </tt>
  );
};

export default CollectionPage;
