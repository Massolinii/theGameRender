import React, { useContext, useState } from "react";
import { AuthContext } from "../AuthContext";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faCheck,
  faCopy,
  faExternalLinkAlt,
  faPencil,
  faHeart as solidHeart,
} from "@fortawesome/free-solid-svg-icons";
import { faHeart as outlineHeart } from "@fortawesome/free-regular-svg-icons";
import "../css/ImageCard.css";
import ImageEditModal from "./ImageEditModal";
import { updateImage } from "../api";

const ImageCard = ({
  image,
  handleImageClick,
  handleCopyClick,
  copiedImageId,
  selectedImages = [],
  isFavorite,
  handleFavoriteToggle,
}) => {
  const { user } = useContext(AuthContext);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);

  const openEditModal = () => {
    setIsEditModalOpen(true);
  };

  const closeEditModal = () => {
    setIsEditModalOpen(false);
  };

  const handleImageUpdate = async (imageId, imageData) => {
    try {
      await updateImage(imageId, imageData);
      console.log("The image was updated successfully");
    } catch (error) {
      console.error("Failed to update image:", error);
    }
  };

  return (
    <div key={image.imageID}>
      <img
        src={image.url}
        alt={image.promptText}
        className="image-in-category"
        onClick={() => handleImageClick(image.imageID)}
      />
      <div className="image-icons">
        {user && user.roles && (
          <div
            className="heart-icon"
            onClick={(e) => {
              e.stopPropagation();
              handleFavoriteToggle(image.imageID);
            }}
          >
            <FontAwesomeIcon icon={isFavorite ? solidHeart : outlineHeart} />
          </div>
        )}
        {user && user.roles && user.roles.includes("ROLE_ADMIN") && (
          <div className="modify-icon">
            <FontAwesomeIcon icon={faPencil} onClick={openEditModal} />
          </div>
        )}
        <ImageEditModal
          isOpen={isEditModalOpen}
          onClose={closeEditModal}
          image={image}
          onUpdateSuccess={(message) => console.log(message)}
          handleImageUpdate={handleImageUpdate}
        />
      </div>
      <div
        className={`prompt-text ${
          selectedImages.includes(image.imageID) ? "visible" : ""
        }`}
      >
        {image.promptText}
        <br />
        <div className="copy-section">
          <div onClick={() => handleCopyClick(image.promptText, image.imageID)}>
            {copiedImageId === image.imageID ? (
              <>
                <FontAwesomeIcon icon={faCheck} className="copy-icon" />
                <span className="copy-text">Copied!</span>
              </>
            ) : (
              <>
                <FontAwesomeIcon icon={faCopy} className="copy-icon" />
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
            <FontAwesomeIcon icon={faExternalLinkAlt} className="open-icon" />
            <span className="open-text px-1">Open</span>
          </a>
        </div>
      </div>
    </div>
  );
};

export default ImageCard;
