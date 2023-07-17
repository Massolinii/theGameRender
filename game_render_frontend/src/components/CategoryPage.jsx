import React, { useContext, useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import {
  fetchCategory,
  fetchCollectionsFromCategory,
  fetchImagesFromCategory,
} from "../api";
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
  faHouseChimney,
  faExternalLinkAlt,
} from "@fortawesome/free-solid-svg-icons";
import CollectionCreateModal from "./CollectionCreateModal";

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
  const [copiedImageId, setCopiedImageId] = useState(null);

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

  const openCollectionCreateModal = () => {
    setIsCollectionCreateModalOpen(true);
  };

  const closeCollectionCreateModal = () => {
    setIsCollectionCreateModalOpen(false);
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

  if (!category) {
    return <div>Loading...</div>;
  }

  const handleUploadSuccess = async (message) => {
    setUploadMessage(message);

    try {
      const images = await fetchImagesFromCategory(id);
      setImages(images);
    } catch (error) {
      console.error("Failed to fetch images:", error);
    }
  };

  const handleCollectionCreateSuccess = async (message) => {
    try {
      const collections = await fetchCollectionsFromCategory(id);
      setCollections(collections);
    } catch (error) {
      console.error("Failed to fetch collections:", error);
    }
  };

  return (
    <tt>
      <div className="pt-5">
        <div className={`category-page-bg ${bgClass}`}>
          <h1 className="category-page-name">{category.categoryName}</h1>
        </div>

        <div className="category-page-container text-white pt-2">
          <Link className="go-back-home p-3" to={"/"}>
            <FontAwesomeIcon icon={faHouseChimney} /> Return Home
          </Link>

          <h3 className="mt-4 px-3 py-1 to-color">
            {category.categoryName} Themes
            {user && user.roles && user.roles.includes("ROLE_ADMIN") && (
              <Button
                className="add-collection ms-2 ps-2 pe-2 pt-1 pb-1"
                variant="outline-light"
                onClick={openCollectionCreateModal}
              >
                <FontAwesomeIcon icon={faPlus} />
              </Button>
            )}
          </h3>
          <CollectionCreateModal
            isOpen={isCollectionCreateModalOpen}
            onClose={closeCollectionCreateModal}
            onCreateSuccess={handleCollectionCreateSuccess}
          />
          <p className="px-3 h3-subtitle">Select a theme </p>
          <ul className="list-unstyled ps-3">
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

          <h3 className="mt-4 px-3 py-1 to-color">
            All {category.categoryName} images{" "}
            {user && user.roles && user.roles.includes("ROLE_ADMIN") && (
              <Button
                className="add-image ps-2 pe-2 pt-1 pb-1"
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
                  <div className="copy-section">
                    <div
                      onClick={() =>
                        handleCopyClick(image.promptText, image.imageID)
                      }
                    >
                      {copiedImageId === image.imageID ? (
                        <>
                          <FontAwesomeIcon
                            icon={faCheck}
                            className="copy-icon"
                          />
                          <span className="copy-text">Copied!</span>
                        </>
                      ) : (
                        <>
                          <FontAwesomeIcon
                            icon={faCopy}
                            className="copy-icon"
                          />
                          <span className="copy-text">Copy</span>
                        </>
                      )}
                    </div>
                    <a
                      href={image.url}
                      target="_blank"
                      rel="noopener noreferrer"
                      className="open-section"
                    >
                      <FontAwesomeIcon
                        icon={faExternalLinkAlt}
                        className="open-icon"
                      />
                      <span className="open-text px-1">Open</span>
                    </a>
                  </div>
                </div>
              </div>
            ))}
          </div>
          <Link className="go-back-home p-3" to={"/"}>
            <FontAwesomeIcon icon={faHouseChimney} /> Return Home
          </Link>
        </div>
      </div>
    </tt>
  );
}

export default CategoryPage;
