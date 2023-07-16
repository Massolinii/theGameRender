import React, { useContext, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { fetchCollection, fetchImagesFromCollection } from "../api";
import "../css/CollectionPage.css";
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
} from "@fortawesome/free-solid-svg-icons";

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
    setTimeout(() => setCopiedImageId(null), 2500);
  };

  if (!collection) {
    return <div>Loading...</div>;
  }

  const bgClass = `collection-category-bg ${collection.categoryName}-category-bg`;

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
    <div>
      <div className={`category-page-bg ${bgClass}`}>
        <h1 className="category-page-name">{collection.collectionName}</h1>
      </div>

      <div className="category-page-container text-white">
        <Link className="go-back-home" to={"/"}>
          <FontAwesomeIcon icon={faCircleLeft} /> Return Home
        </Link>

        <h3 className="pt-4 ps-2">
          All {collection.collectionName} images{" "}
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

        {uploadMessage && <Alert variant="success">{uploadMessage}</Alert>}

        <ImageUploadModal
          isOpen={isUploadModalOpen}
          onClose={closeUploadModal}
          onUploadSuccess={handleUploadSuccess}
        />
        <div className="image-container">
          {images.map((image) => (
            <div key={image.imageID}>
              <img
                src={image.url}
                alt=""
                className="image-in-category"
                onClick={() => handleImageClick(image.imageID)}
              />
              <div
                className={`prompt-text ${
                  selectedImages.includes(image.imageID) ? "visible" : ""
                }`}
              >
                {image.promptText}
                <br />
                {copiedImageId === image.imageID ? (
                  <div className="copy-section">
                    <FontAwesomeIcon icon={faCheck} className="copy-icon" />
                    <span className="copy-text">Copied!</span>
                  </div>
                ) : (
                  <div className="copy-section">
                    <FontAwesomeIcon
                      icon={faCopy}
                      onClick={() =>
                        handleCopyClick(image.promptText, image.imageID)
                      }
                      className="copy-icon"
                    />
                    <span className="copy-text">Copy</span>
                  </div>
                )}
              </div>
            </div>
          ))}
        </div>
        <Link className="go-back-home" to={"/"}>
          <FontAwesomeIcon icon={faCircleLeft} /> Return Home
        </Link>
      </div>
    </div>
  );
};

export default CollectionPage;
