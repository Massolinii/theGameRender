import React from "react";
import { useHistory } from "react-router-dom";

const ProfilePage = ({ user, handleDeleteAccount }) => {
  const history = useHistory();

  const onDeleteAccount = () => {
    handleDeleteAccount(user.id).then(() => {
      history.push("/"); // Redirects the user to home page after account deletion
    });
  };

  return (
    <div className="profile-page">
      <h1>Profilo Utente</h1>
      <div className="profile-details">
        <p>
          <strong>Username:</strong> {user.username}
        </p>
        <p>
          <strong>Email:</strong> {user.email}
        </p>
      </div>
      <button onClick={onDeleteAccount}>Cancella Account</button>
    </div>
  );
};

export default ProfilePage;
