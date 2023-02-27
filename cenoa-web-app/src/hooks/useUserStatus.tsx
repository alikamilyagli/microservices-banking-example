import React, { useState, useEffect } from 'react';
import Auth from '@aws-amplify/auth';
import { Hub } from '@aws-amplify/core';

function useUserStatus() {
  let [user, setUser] = useState(null)

  useEffect(() => {
    let updateUser = async () => {
      try {
        let user = await Auth.currentAuthenticatedUser()
        setUser(user)
      } catch {
        setUser(null)
      }
    }
    Hub.listen('auth', updateUser)

    updateUser()
    return () => Hub.remove('auth', updateUser)
  }, []);

  return user;
}

export default useUserStatus;
