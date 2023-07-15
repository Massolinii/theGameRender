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
import copy from 'clipboard-copy';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCopy, faPlus, faCheck, faCircleLeft } from '@fortawesome/free-solid-svg-icons'

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
    setTimeout(() => setCopiedImageId(null), 4000);  // l'icona torna alla copia dopo 4 secondi
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

  return (
    <tt>
    <div className="pt-5">
      <div className={`category-page-bg ${bgClass}`}>
        <h1 className="category-page-name">{category.categoryName}</h1>
      </div>

      <div className="category-page-container text-white">

        <Link className="go-back-home" to={"/"}>
        <FontAwesomeIcon icon={faCircleLeft} /> Return Home
        </Link>
        
        <h3 className="pt-4 ps-2">Collections</h3>
        <ul>
          {collections.map((collection) => (
            <li key={collection.collectionID}>{collection.collectionName}</li>
          ))}
        </ul>

        <h3 className="pt-4 ps-2">All {category.categoryName} images {user && user.roles.includes("ROLE_ADMIN") && (
          <Button className="add-image" variant="outline-light" onClick={openUploadModal}><FontAwesomeIcon icon={faPlus} /></Button>
        )}</h3>
        
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
             <div className={`prompt-text ${selectedImages.includes(image.imageID) ? 'visible' : ''}`}>
              {image.promptText}
              <br />
              {copiedImageId === image.imageID ? (
                <div className="copy-section">
                  <FontAwesomeIcon icon={faCheck} className='copy-icon' />
                  <span className="copy-text">Copied!</span>
                </div>
              ) : (
                <div className="copy-section">
                  <FontAwesomeIcon icon={faCopy} onClick={() => handleCopyClick(image.promptText, image.imageID)} className='copy-icon' />
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
    </tt>
  );
}

export default CategoryPage;
