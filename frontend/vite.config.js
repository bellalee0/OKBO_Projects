import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      '/users': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/boards': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/comments': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/likes': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
      '/follows': {
        target: 'http://localhost:8080',
        changeOrigin: true,
      },
    },
  },
})
