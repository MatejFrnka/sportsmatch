import '../styles/Avatar.css'

interface AvatarProps {
  src: string
}

const Avatar: React.FC<AvatarProps> = ({ src }) => (
  <div className="avatar" style={{ backgroundImage: `url(${src}` }}></div>
)

export default Avatar
