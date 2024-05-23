/// <reference types="vitest" />
/// <reference types="vite/client" />

import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  test: {
    globals: true,
    environment: "jsdom",
    setupFiles: './src/test/setup.ts',
    //configuration from https://github.com/vitest-dev/vitest/blob/main/examples/react-testing-lib/vite.config.ts
    //css might be disabled if we are not 
    //going to hove test that rely on CSS
    css: true,
  }
})
