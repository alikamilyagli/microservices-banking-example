// This hook is used to fix React Hydration Error on any page
import { useState, useEffect } from 'react';

const useMounted = () => {
  const [hasMounted, setHasMounted] = useState(false);
  useEffect(() => {
    setHasMounted(true)
  }, []);
	return hasMounted;
};

export default useMounted;
